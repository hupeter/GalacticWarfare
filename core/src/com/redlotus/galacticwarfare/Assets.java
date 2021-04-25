package com.redlotus.galacticwarfare;

public enum Assets {
    SCI_FI_MUSIC("scifi.ogg",
            "Sci-fi Laboratory Ambience by Iwan Gabovitch under CC-BY 3.0 License with sounds by wolick, swiftoid, Diboz"),
    DROP_PICTURE("drop.png", "https://raw.githubusercontent.com/Quillraven/SimpleKtxGame/01-app/android/assets/images/drop.png"),
    BUCKET_PICTURE("bucket.png", "https://raw.githubusercontent.com/Quillraven/SimpleKtxGame/01-app/android/assets/images/bucket.png")
    ;

    private String attribution;
    private String fileName;

    private Assets(String fileName, String attribution){
        this.fileName = fileName;
        this.attribution = attribution;
    }

    public String getAttribution() {
        return attribution;
    }

    public String getFileName() {
        return fileName;
    }
}
