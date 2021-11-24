package cz.vutbr.feec.utko.bpcmds.projekt;

public class Video {

    private String URL = null;
    private String name = null;

    public Video(String file, String file_name) {
        this.URL = file;
        this.name = file_name;
    }


    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}