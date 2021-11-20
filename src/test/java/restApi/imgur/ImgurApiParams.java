package restApi.imgur;

public final class ImgurApiParams {

    private static final String TOKEN = "586aca4ac3e1125534ca097029415dc673a449b3";
    private static final String API_VER = "3";
    private static final String ALBUM_HASH = "mUtJOfL";
    private static final String IMAGE_HASH = "6Ph4wcz";

    public static String getTOKEN() {
        return TOKEN;
    }

    public static String getApiVer() {
        return API_VER;
    }

    public static String getAlbumHash() {
        return ALBUM_HASH;
    }

    public static String getImageHash() {
        return IMAGE_HASH;
    }
}
