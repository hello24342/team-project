package data_access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Flashcard;
import entity.Language;
import usecase.FlashcardDataAccessInterface;

public class FileFlashcardDataAccessObject implements FlashcardDataAccessInterface {
    private static final String HEADER = "id,sourceWord,targetWord,sourceLang,targetLang,known,deckIds";

    private final File csvFile;
    private int nextId = 1;
    private final Map<Integer, Flashcard> flashcards = new HashMap<>();

    public FileFlashcardDataAccessObject(String csvPath) {
        csvFile = new File(csvPath);
        try {
            if (!csvFile.exists()) {
                csvFile.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
                    writer.write(HEADER);
                    writer.newLine();
                }
            }
            loadFromFile();
        }
        catch (IOException ex) {
            throw new RuntimeException("Failed to initialize Flashcard DAO", ex);
        }
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            final String header = reader.readLine();
            if (!HEADER.equals(header)) {
                throw new RuntimeException("Header should be: " + HEADER + "\nBut was: " + header);
            }

            String row;
            while ((row = reader.readLine()) != null) {
                String[] cols = splitCsvRow(row);

                int id = Integer.parseInt(cols[0]);
                String sourceWord = cols[1];
                String targetWord = cols[2];
                Language sourceLang = Language.valueOf(cols[3]);
                Language targetLang = Language.valueOf(cols[4]);
                boolean known = Boolean.parseBoolean(cols[5]);
                List<Integer> deckIds = parseDeckIds(cols[6]);

                Flashcard card = new Flashcard(id, sourceWord, targetWord, sourceLang, targetLang);
                card.setKnown(known);
                card.getDeckIds().addAll(deckIds);

                flashcards.put(id, card);
                nextId = Math.max(nextId, id + 1);
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(HEADER);
            writer.newLine();

            for (Flashcard card : flashcards.values()) {
                String deckIds = formatDeckIds(card.getDeckIds());
                String line = String.format("%d,%s,%s,%s,%s,%b,\"%s\"", card.getId(), card.getSourceWord(),
                        card.getTargetWord(), card.getSourceLang().name(), card.getTargetLang().name(), card.isKnown(), deckIds);
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // helpers
    private String[] splitCsvRow(String row) {
        // splits by commas unless inside quotes
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (char c : row.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            }
            else if (c == ',' && !inQuotes) {
                tokens.add(sb.toString());
                sb.setLength(0);
                continue;
            }
            sb.append(c);
        }
        tokens.add(sb.toString());
        return tokens.toArray(new String[0]);
    }

    private List<Integer> parseDeckIds(String deckIds) {
        List<Integer> list = new ArrayList<>();
        if (deckIds == null || deckIds.isEmpty()) {
            return list;
        }

        // Remove surrounding quotes
        deckIds = deckIds.replace("\"", "");
        if (deckIds.trim().isEmpty()) {
            return list;
        }

        for (String part : deckIds.split(",")) {
            list.add(Integer.parseInt(part));
        }
        return list;
    }

    private String formatDeckIds(List<Integer> deckIds) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < deckIds.size(); i++) {
            sb.append(deckIds.get(i));
            if (i < deckIds.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    // interface methods
    @Override
    public int nextFlashcardId() {
        return nextId++;
    }

    @Override
    public void save(Flashcard card) {
        flashcards.put(card.getId(), card);
        saveToFile();
    }

    @Override
    public void update(int cardId) {

    }

    @Override
    public void delete(int cardId) {

    }

    @Override
    public Flashcard findById(int cardId) {
        return flashcards.get(cardId);
    }

    @Override
    public List<Flashcard> findByDeck(int deckId) {
        List<Flashcard> result = new ArrayList<>();
        for (Flashcard card : flashcards.values()) {
            if (card.getDeckIds().contains(deckId)) {
                result.add(card);
            }
        }
        return result;
    }
    @Override
    public void markCardAsKnown(int userId, int deckId, int cardIndex) {
        // To track that the user knows this card (no need to move)
        // TODO: Implement method for mark known
    }

    @Override
    public void markCardAsUnknown(int cardIndex, int fromDeckId, int toDeckId) {
        List<Flashcard> fromDeck = findByDeck(fromDeckId);

        Flashcard cardToMove = fromDeck.get(cardIndex);
        cardToMove.getDeckIds().add(toDeckId);
    }

}
