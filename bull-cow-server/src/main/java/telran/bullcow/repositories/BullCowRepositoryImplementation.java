package telran.bullcow.repositories;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.*;
import jakarta.persistence.spi.*;
import telran.bullcow.entities.*;
import telran.bullcow.exceptions.*;

public class BullCowRepositoryImplementation implements BullCowRepository {

    private EntityManager em;

    public BullCowRepositoryImplementation(PersistenceUnitInfo persistenceUnit,
            HashMap<String, Object> properties) {
        try {
            String providerName = persistenceUnit.getPersistenceProviderClassName();
            @SuppressWarnings("unchecked")
            Class<PersistenceProvider> clazz = (Class<PersistenceProvider>) Class.forName(providerName);
            Constructor<PersistenceProvider> constructor = clazz.getConstructor();
            PersistenceProvider provider = constructor.newInstance();
            EntityManagerFactory emf = provider.createContainerEntityManagerFactory(persistenceUnit, properties);
            em = emf.createEntityManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Game getGame(Long id) {
        return em.find(Game.class, id);
    }

    @Override
    public Long createGame(Game game, GameGamer gameGamer) {
        Long id;
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.persist(game);
            em.persist(gameGamer);
            id = game.getId();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
        return id;
    }

    @Override
    public List<Long> getJoinableGames(String username) {
        TypedQuery<Long> query = em.createQuery(
                "select g.id from Game g where g.id in " +
                        "(select gg.game.id from GameGamer gg where gg.gamer.username != :username) " +
                        "and g.startDate is null",
                Long.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    public List<Long> getStartableGames(String username) {
        TypedQuery<Long> query = em.createQuery(
                "select g.id from Game g where g.id in " +
                        "(select gg.game.id from GameGamer gg where gg.gamer.username = :username) " +
                        "and g.startDate is null",
                Long.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    public void setGameStartDate(Long gameId) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            Game game = getGame(gameId);
            if (game == null)
                throw new GameNotFoundException(gameId);
            game.setStartDate(LocalDateTime.now());
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void setFinishGame(Long gameId) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            Game game = getGame(gameId);
            if (game == null)
                throw new GameNotFoundException(gameId);
            game.finishGame();
            em.merge(game);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public Gamer getGamer(String username) {
        return em.find(Gamer.class, username);
    }

    @Override
    public void createGamer(Gamer gamer) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            Gamer existingGamer = getGamer(gamer.getUsername());
            if (existingGamer != null)
                throw new GamerAlreadyExistsException(existingGamer.getUsername());
            em.merge(gamer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void joinGame(String gamerUsername, Long gameId) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            GameGamer existingGameGamer = getGameGamer(gamerUsername, gameId);
            if (existingGameGamer != null)
                throw new GameGamerAlreadyExistsException(existingGameGamer);
            GameGamer gameGamer = new GameGamer(getGame(gameId), getGamer(gamerUsername));
            em.merge(gameGamer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public GameGamer getGameGamer(String gamerUsername, Long gameId) {
        TypedQuery<GameGamer> query = em.createQuery("select gg from GameGamer gg " +
                "where gg.gamer.username = :gamerUsername and gg.game.id = :gameId",
                GameGamer.class);
        query.setParameter("gamerUsername", gamerUsername);
        query.setParameter("gameId", gameId);
        return query.getSingleResult();
    }

    @Override
    public Move makeMove(String gamerUsername, Long gameId, String sequence) {
        Move move;
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            GameGamer gameGamer = getGameGamer(gamerUsername, gameId);
            if (gameGamer == null)
                throw new GameGamerNotFoundException();
            move = new Move(gameGamer, sequence);
            em.merge(move);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
        return move;
    }
}
