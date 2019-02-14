package test.subjects;

import model.PostDTO;

public class JsonPlaceholderSamplePosts {

    private JsonPlaceholderSamplePosts(){}

    public static PostDTO getFirstPost(){
        int expectedUserId = 1;
        int expectedId = 1;
        String expectedTitle = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit";
        String expectedBody = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto";

        return new PostDTO(expectedUserId, expectedId, expectedTitle, expectedBody);
    }

    public static PostDTO get42ndPost(){
        int expectedUserId = 5;
        int expectedId = 42;
        String expectedTitle = "commodi ullam sint et excepturi error explicabo praesentium voluptas";
        String expectedBody = "odio fugit voluptatum ducimus earum autem est incidunt voluptatem\nodit reiciendis aliquam sunt sequi nulla dolorem\nnon facere repellendus voluptates quia\nratione harum vitae ut";

        return new PostDTO(expectedUserId, expectedId, expectedTitle, expectedBody);
    }

    public static PostDTO getLastPost(){
        int expectedUserId = 10;
        int expectedId = 100;
        String expectedTitle = "at nam consequatur ea labore ea harum";
        String expectedBody = "cupiditate quo est a modi nesciunt soluta\nipsa voluptas error itaque dicta in\nautem qui minus magnam et distinctio eum\naccusamus ratione error aut";

        return new PostDTO(expectedUserId, expectedId, expectedTitle, expectedBody);
    }
}
