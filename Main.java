import java.security.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        // Генерація ключів для підпису
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // Створення транзакцій
        List<String> outputs1 = new ArrayList<>();
        outputs1.add("address1");
        outputs1.add("address2");
        Transaction tx1 = new Transaction("senderAddress", outputs1, 10.0, System.currentTimeMillis());
        tx1.signTransaction(privateKey);
        System.out.println(tx1);

        // Перевірка підпису транзакції
        boolean isTxValid = tx1.verifyTransactionSignature(publicKey);
        System.out.println("Transaction 1 signature valid: " + isTxValid);

        // Створення блока
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(tx1);
        Block block1 = new Block(1, "prevHash", System.currentTimeMillis(), 4, 0, "merkleRoot", transactions);
        block1.signBlock(privateKey);
        System.out.println(block1);

        // Перевірка підпису блока
        boolean isBlockValid = block1.verifyBlockSignature(publicKey);
        System.out.println("Block 1 signature valid: " + isBlockValid);

        // Додавання блоку до блокчейну
        Blockchain blockchain = new Blockchain();
        blockchain.addBlock(block1);
        System.out.println(blockchain);
    }
}
