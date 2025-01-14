package telran.bullcow;

import java.util.HashMap;
import java.util.Scanner;

import jakarta.persistence.spi.PersistenceUnitInfo;
import telran.bullcow.config.BullsCowsPersistenceUnitInfo;
import telran.bullcow.protocols.BullCowProtocol;
import telran.bullcow.repositories.BullCowRepository;
import telran.bullcow.repositories.BullCowRepositoryImplementation;
import telran.bullcow.services.BullCowService;
import telran.bullcow.services.BullCowServiceImplementation;
import telran.net.TcpServer;

public class Main {
    private static final int PORT = 4000;

    public static void main(String[] args) {
        TcpServer tcpServer = createTcpServer();
        new Thread(tcpServer).start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("enter shutdown for stopping server");
            String line = scanner.nextLine();
            if (line.equals("shutdown")) {
                tcpServer.shutdown();
                scanner.close();
                break;
            }
        }
    }

    private static TcpServer createTcpServer() {
        HashMap<String, Object> hibernateProperties = new HashMap<>();
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
        PersistenceUnitInfo persistenceUnit = new BullsCowsPersistenceUnitInfo();
        BullCowRepository repository = new BullCowRepositoryImplementation(persistenceUnit, hibernateProperties);
        BullCowService service = new BullCowServiceImplementation(repository);
        BullCowProtocol protocol = new BullCowProtocol(service);
        return new TcpServer(protocol, PORT);
    }

}