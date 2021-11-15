public class Vigene {
    private String key;
    public Vigene(String key) {
        super();
        this.key = key;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    @Override
    public String toString() {
        return String.format("%s", this.key);
    }
    protected String rebuildHash(String hash) {
        StringBuilder s = new StringBuilder();
        int p = 0;
        for(int i = 0; i < key.length(); i++) {
            if(p == hash.length()) p = 0;
            s.append(hash.charAt(p));
            p++;
        }
        return s.toString();
    }
    protected String crypt(String[][] temp, String hash) {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < key.length(); i++) {
            s.append(temp[(hash.charAt(i) & 31) - 2][(key.charAt(i) & 31) - 1].toUpperCase());
        }
        return s.toString();
    }
}
