package restApi.imgur;

public final class Endpoints {

    private static final String API_URL = "https://api.imgur.com";
    private static final String BASE_URI =
            API_URL + "/" + ImgurApiParams.getApiVer() + "/album/" + ImgurApiParams.getAlbumHash();
    private static final String ALBUM_URL = "/image/" + ImgurApiParams.getImageHash();
    private static final String ADD_IMAGE_URL = "/add";
    private static final String REMOVE_IMAGE_URL = "/remove_images";
    private static final String FAVOURITE_URL = "/favorite";

    public static String getBaseUri() {
        return BASE_URI;
    }

    public static String getAlbumUrl() {
        return ALBUM_URL;
    }

    public static String getAddImageUrl() {
        return ADD_IMAGE_URL;
    }

    public static String getRemoveImageUrl() {
        return REMOVE_IMAGE_URL;
    }

    public static String getFavouriteUrl() {
        return FAVOURITE_URL;
    }
}
