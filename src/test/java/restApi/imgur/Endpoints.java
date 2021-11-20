package restApi.imgur;

public final class Endpoints {

    public static String API_URL = "https://api.imgur.com";
    public static String BASE_URI =
            API_URL + "/" + ImgurApiParams.API_VER + "/album/" + ImgurApiParams.ALBUM_HASH;
    public static String ALBUM_URL = "/image/" + ImgurApiParams.IMAGE_HASH;
    public static String ADD_IMAGE_URL= "/add";
    public static String REMOVE_IMAGE_URL = "/remove_images";
    public static String FAVOURITE_URL = "/favorite";

}
