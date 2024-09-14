import java.util.*;

public class Main {

    static class Node implements Comparable<Node> {
        char data;
        int frequency;
        Node left, right;

        public Node(char data, int frequency) {
            this.data = data;
            this.frequency = frequency;
            left = right = null;
        }

        @Override
        public int compareTo(Node other) {
            return this.frequency - other.frequency;
        }
    }

    static class Tree {
        Node root;

        public Tree(Node root) {
            this.root = root;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your sentence: ");
        String input = scanner.nextLine();
        scanner.close();

        Map<Character, Integer> frequencyMap = new HashMap<>();

        // Count the frequency of each letter
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }

        // Print the frequency of each letter
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            System.out.printf("'%c' has a frequency of %d%n", entry.getKey(), entry.getValue());
        }

        // Create Huffman trees for each letter and add them to a priority queue
        PriorityQueue<Tree> queue = new PriorityQueue<>((t1, t2) -> t1.root.frequency - t2.root.frequency);
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            Tree tree = new Tree(node);
            queue.offer(tree);
        }

        // Combine trees until only one is left
        while (queue.size() > 1) {
            Tree t1 = queue.poll();
            Tree t2 = queue.poll();
            Node combinedRoot = new Node('\0', t1.root.frequency + t2.root.frequency);
            combinedRoot.left = t1.root;
            combinedRoot.right = t2.root;
            Tree combinedTree = new Tree(combinedRoot);
            queue.offer(combinedTree);
        }

        // Derive Huffman encoding using the resulting Huffman tree
        Tree huffmanTree = queue.poll();
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateHuffmanCodes(huffmanTree.root, "", huffmanCodes);

        // Print Huffman codes
        System.out.println("\nHuffman Codes:");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.printf("'%c' : %s%n", entry.getKey(), entry.getValue());
        }
    }

    private static void generateHuffmanCodes(Node root, String code, Map<Character, String> huffmanCodes) {
        if (root == null) {
            return;
        }
        if (root.data != '\0') {
            huffmanCodes.put(root.data, code);
        }
        generateHuffmanCodes(root.left, code + "0", huffmanCodes);
        generateHuffmanCodes(root.right, code + "1", huffmanCodes);
    }
}
