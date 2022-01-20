//Shahrizod Bobojonov 114137520 Recitation 1

import java.io.Serializable;

/**
 * Storage class representing a storage box and implements Serializable, so
 * that the object can be read in and written to a file. Each instance has
 * id, client, and contents fields representing attributes of the box.
 */
public class Storage implements Serializable {
    private int id;
    private String client;
    private String contents;

    /**
     * Arg constructor for a Storage object such that the fields can be initialized all at once
     *
     * @param inId
     *      the value to set id of the object to
     * @param inClient
     *      the value to set client of the object to
     * @param inContents
     *      the value to set contents of the object to
     * @throws IllegalArgumentException
     *      when id is < 0 or client or contents are null or blank
     */
    public Storage(int inId, String inClient, String inContents) throws IllegalArgumentException {
        if (inId < 0 || inClient == null || inClient.isBlank() ||
                inContents == null || inContents.isBlank())
            throw new IllegalArgumentException();
        id = inId;
        client = inClient;
        contents = inContents;
    }

    /**
     * Accessor method for the id field
     *
     * @return
     *      the id field of the Storage object
     */
    public int getId() {
        return id;
    }

    /**
     * Mutator method for the id field
     *
     * @param id
     *      the new value of id to set the field to
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Accessor method for the client field
     *
     * @return
     *      the client field of the Storage object
     */
    public String getClient() {
        return client;
    }

    /**
     * Mutator method for the client field
     *
     * @param client
     *      the new value of client to set the field to
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * Accessor method for the contents field
     *
     * @return
     *      the contents field of the Storage object
     */
    public String getContents() {
        return contents;
    }

    /**
     * Mutator method for the contents field
     *
     * @param contents
     *      the new value of contents to set the field to
     */
    public void setContents(String contents) {
        this.contents = contents;
    }
}
