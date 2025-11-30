package data_access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entity.FlashcardDeck;
import use_case.deck.DeckDataAccessInterface;

/**
 * DAO for deck data implemented using a CSV file
 * to persist the data.
 * This class belongs in the data_access layer and
 * implements the DeckDataAccessInterface from the
 * usecase layer. Use cases do not know that the
 * data is stored in CSV; they only depend on the
 * interface.
 */
public class FileDeckDataAccessObject implements DeckDataAccessInterface {

    /**
     * The expected CSV header line.
     * Columns: deck id, user id, title, is_dont_know
     */
    private static final String HEADER =
            "deck_id,user_id,title,is_dont_know";

    /**
     * The CSV file where deck data is stored.
     */
    private final File csvFile;

    /**
     * Mapping from column name to column index.
     * For example: "deck_id" -> 0, "user_id" -> 1.
     */
    private final Map<String, Integer> headers =
            new LinkedHashMap<String, Integer>();

    /**
     * Main in-memory data structure for decks.
     * Key: deck id
     * Value: FlashcardDeck entity
     */
    private final Map<Integer, FlashcardDeck> decksById =
            new HashMap<Integer, FlashcardDeck>();

    /**
     * Mapping from user id to that user's
     * "Don't Know" deck id (if it exists).
     */
    private final Map<Integer, Integer> dontKnowDeckIdByUser =
            new HashMap<Integer, Integer>();

    /**
     * Next deck id to assign when creating
     * a new deck.
     */
    private int nextDeckId = 1;

    /**
     * Construct this DAO for saving to and reading from
     * a local CSV file.
     *
     * @param csvPath the path of the file to save to
     * @throws RuntimeException if there is an IOException
     */
    public FileDeckDataAccessObject(String csvPath) {

        csvFile = new File(csvPath);

        // Define the header order for columns.
        headers.put("deck_id", 0);
        headers.put("user_id", 1);
        headers.put("title", 2);
        headers.put("is_dont_know", 3);

        // If the file does not exist yet, create it.
        try {
            if (!csvFile.exists()) {
                csvFile.createNewFile();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // If the file is empty, write only the header.
        if (csvFile.length() == 0) {
            saveToFile();
        }
        else {
            // Otherwise, load existing data from the file.
            loadFromFile();
        }
    }

    // ==================== File read/write ====================

    /**
     * Load all deck data from the CSV file into memory.
     * This method populates decksById, dontKnowDeckIdByUser,
     * and nextDeckId.
     */
    private void loadFromFile() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new FileReader(csvFile)
            );

            // Read and check the header row.
            String header = reader.readLine();
            if (header == null) {
                // File has no content at all, just write header
                // and return.
                saveToFile();
                return;
            }

            if (!HEADER.equals(header)) {
                throw new RuntimeException(
                        String.format(
                                "header should be%n%s%nbut was%n%s",
                                HEADER, header
                        )
                );
            }

            // Clear any existing in-memory data
            // and reset nextDeckId.
            decksById.clear();
            dontKnowDeckIdByUser.clear();
            nextDeckId = 1;

            // Read each subsequent line as a deck record.
            String row = reader.readLine();
            while (row != null) {
                String[] col = row.split(",");

                int deckId = Integer.parseInt(
                        col[headers.get("deck_id")]
                );
                int userId = Integer.parseInt(
                        col[headers.get("user_id")]
                );
                String title = col[headers.get("title")];
                String isDontKnowStr = col[headers.get(
                        "is_dont_know"
                )];
                boolean isDontKnow =
                        isDontKnowStr.equals("true");

                // Reconstruct the FlashcardDeck from the CSV row.
                FlashcardDeck deck =
                        new FlashcardDeck(deckId, title, userId);

                // Store the deck by its id.
                decksById.put(Integer.valueOf(deckId), deck);

                // If this is a "Don't Know" deck, record it
                // for that user.
                if (isDontKnow) {
                    dontKnowDeckIdByUser.put(
                            Integer.valueOf(userId),
                            Integer.valueOf(deckId)
                    );
                }

                // Keep track of the largest id so we can compute
                // the next available id.
                if (deckId >= nextDeckId) {
                    nextDeckId = deckId + 1;
                }

                row = reader.readLine();
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        finally {
            // Close the reader if it was opened.
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Write the current in-memory deck data to the CSV file.
     * This overwrites the entire file with the header and all
     * deck rows.
     */
    private void saveToFile() {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(
                    new FileWriter(csvFile)
            );

            // Write header row.
            writer.write(HEADER);
            writer.newLine();

            // Write one line per deck.
            for (FlashcardDeck deck : decksById.values()) {
                int deckId = deck.getId();
                int userId = deck.getUserId();
                String title = deck.getTitle();

                // Determine whether this deck is the "Don't Know"
                // deck for this user.
                boolean isDontKnow =
                        dontKnowDeckIdByUser.containsKey(
                                Integer.valueOf(userId)
                        )
                                && dontKnowDeckIdByUser.get(
                                Integer.valueOf(userId)
                        ).intValue() == deckId;

                String line = String.format(
                        "%d,%d,%s,%s",
                        Integer.valueOf(deckId),
                        Integer.valueOf(userId),
                        title,
                        String.valueOf(isDontKnow)
                );
                writer.write(line);
                writer.newLine();
            }

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        finally {
            // Close the writer if it was opened.
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // ==================== Interface methods ====================

    /**
     * Return true if there already exists a deck with the
     * given title for this user.
     */
    @Override
    public boolean existsByTitleForUser(int userId, String title) {
        // Scan all decks and check userId + title match.
        for (FlashcardDeck deck : decksById.values()) {
            if (deck.getUserId() == userId
                    && deck.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the next available deck id and increment the counter.
     * Note: this does not write to the file immediately; the updated
     * value will be persisted on the next saveToFile() call.
     */
    @Override
    public int nextDeckId() {
        int id = nextDeckId;
        nextDeckId = nextDeckId + 1;
        return id;
    }

    /**
     * Save or update a deck in memory and then persist all decks
     * to the CSV file.
     */
    @Override
    public void save(FlashcardDeck deck) {
        decksById.put(
                Integer.valueOf(deck.getId()),
                deck
        );
        saveToFile();
    }

    /**
     * Return all decks that belong to the given user.
     */
    @Override
    public List<FlashcardDeck> findByUser(int userId) {
        List<FlashcardDeck> result =
                new ArrayList<FlashcardDeck>();
        for (FlashcardDeck deck : decksById.values()) {
            if (deck.getUserId() == userId) {
                result.add(deck);
            }
        }
        return result;
    }

    /**
     * Find a deck by its deck id.
     */
    @Override
    public FlashcardDeck findById(int deckId) {
        return decksById.get(Integer.valueOf(deckId));
    }

    /**
     * Get the id of this user's "Don't Know" deck.
     * If it does not exist yet, create it, save it,
     * and then return its id.
     */
    @Override
    public int findOrCreateDontKnowDeckId(int userId) {
        Integer key = Integer.valueOf(userId);
        Integer existing = dontKnowDeckIdByUser.get(key);
        if (existing != null) {
            return existing.intValue();
        }

        // No "Don't Know" deck yet: create one.
        int newId = nextDeckId();
        FlashcardDeck dontKnowDeck =
                new FlashcardDeck(
                        newId,
                        "Don't know deck",
                        userId
                );

        decksById.put(Integer.valueOf(newId), dontKnowDeck);
        dontKnowDeckIdByUser.put(
                key, Integer.valueOf(newId)
        );
        saveToFile();
        return newId;
    }
}