import java.util.*;

class BaseGFSClient {
    private Map<String, String> chunk_list;
    public BaseGFSClient() {}
    public String readChunk(String filename, int chunkIndex) {
       // Read a chunk from GFS
       return "";
    }
    public void writeChunk(String filename, int chunkIndex, String content) {
       // Write a chunk to GFS
    }
}

public class GFSClient extends BaseGFSClient {
    private int chunkSize;
    private HashMap<String, Integer> chunkNums = new HashMap<>();

    /*
     * @param chunkSize: An integer
     */
    public GFSClient(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    /*
     * @param filename: a file name
     * @return: conetent of the file given from GFS
     */
    public String read(String filename) {
        if (!this.chunkNums.containsKey(filename))
            return null;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.chunkNums.get(filename); i++) {
            sb.append(this.readChunk(filename, i));
        }
        return sb.toString();
    }

    /*
     * @param filename: a file name
     * @param content: a string
     * @return: nothing
     */
    public void write(String filename, String content) {
        int chunkNum = (content.length() - 1) / this.chunkSize + 1;
        this.chunkNums.put(filename, chunkNum);
        
        int i = 0;
        for (i = 0; i < chunkNum-1; i++)
            this.writeChunk(filename, i, content.substring(i*this.chunkSize, i*this.chunkSize + chunkSize));
        this.writeChunk(filename, chunkNum-1, content.substring(i*this.chunkSize, content.length()));
    }
}