package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//@JsonInclude(JsonInclude.Include.NON_NULL) используется во время сериализации. Настройка Include.NON_NULL показывает,
// что null-поля не будут упомянуты в конечном JSON.
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder применяется, если важен порядок свойств. Эта аннотация указывается в ({}) — порядок появления полей.
@JsonPropertyOrder({
        "data",
        "success",
        "status"
})

//Библиотека Lombok на этапе компиляции сгенерирует геттеры\сеттеры для всех полей, toString и
// переопределит equals и hashCode по стандартам. Нужна для сокращения кода.
@lombok.Data
public class AccountInfoResponse {

//    @JsonProperty ставится над полем, которое надо сериализовать или десериализовать. В скобках указывается
//    строковое значение, которое представлено в итоговом или исходном JSON.
    @JsonProperty("data")
    private Data data;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("status")
    private Integer status;


//-----------------------------------com.geekbrains.dto.Data.java-----------------------------------

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "url",
            "bio",
            "avatar",
            "avatar_name",
            "cover",
            "cover_name",
            "reputation",
            "reputation_name",
            "created",
            "pro_expiration",
            "user_follow",
            "is_blocked"
    })

    @lombok.Data
    public class Data {

        @JsonProperty("id")
        private Integer id;
        @JsonProperty("url")
        private String url;
        @JsonProperty("bio")
        private Object bio;
        @JsonProperty("avatar")
        private String avatar;
        @JsonProperty("avatar_name")
        private String avatarName;
        @JsonProperty("cover")
        private String cover;
        @JsonProperty("cover_name")
        private String coverName;
        @JsonProperty("reputation")
        private Integer reputation;
        @JsonProperty("reputation_name")
        private String reputationName;
        @JsonProperty("created")
        private Integer created;
        @JsonProperty("pro_expiration")
        private Boolean proExpiration;
        @JsonProperty("user_follow")
        private UserFollow userFollow;
        @JsonProperty("is_blocked")
        private Boolean isBlocked;

    }
//-----------------------------------com.geekbrains.dto.UserFollow.java-----------------------------------

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "status"
    })

    @lombok.Data
    public static class UserFollow {

        @JsonProperty("status")
        private Boolean status;

    }
}
