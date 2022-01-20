//Shahrizod Bobojonov 114137520 Recitation 1

import java.io.Serializable;
import java.util.HashMap;

/**
 * This class extends the HashMap class because it represents a hashmap data
 * structure where all the storage boxes are stored. It also implements
 * Serializable so that the hashmap can be saved and restored each time the
 * program runs. This way, the storage locker is persistent, meaning that each
 * time the program is run, the previous storage locker can just be loaded in.
 */
public class StorageTable extends HashMap<Integer, Storage> implements Serializable {

    /**
     * Inserts a given Storage object into the HashMap with the given key,
     * calls the put() method from the superclass HashMap
     *
     * @param storageId
     *      the id of the storage box, representing the key of the hashmap
     * @param storage
     *      the Storage object to insert into the hashmap
     * @throws IllegalArgumentException
     *      when the id is < 0, storage is null or if the hashmap already has
     *      a value at the given key (storageId)
     */
    public void putStorage(int storageId, Storage storage) throws IllegalArgumentException {
        if (storageId < 0 || containsKey(storageId) || storage == null) throw new IllegalArgumentException();
        put(storageId, storage);
    }

    /**
     * Gets the value of whatever is stored at the given key of the hashmap,
     * calls the get() method from the superclass HashMap
     *
     * @param storageId
     *      the id of the storage box which represents the key of the hashmap
     * @return
     *      the value of the given key in the hashmap, a Storage object
     */
    public Storage getStorage(int storageId) {
        return get(storageId);
    }

}
