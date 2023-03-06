package homeTry2.User;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class InteractionApi2 {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String ALL_USERS = "https://jsonplaceholder.typicode.com/users";
    private static final Gson GSON = new Gson();


    public static void main(String[] args) throws Exception {


        User coolKid = new InteractionApi2().createUser("Boba Fett", "boba@fett.com");
        coolKid.setName("Cool Kid");
        coolKid.setAddress(new Address("Cool street", "Suite 99", "Cool-Kids-City", "1337", new Geo("111", "222")));
        coolKid.setCompany(new Company("Awesome Inc.", "leeets ggooooo!", "just-doing-cool-things"));

        //uploadUser(coolKid);
        //updateUser(coolKid, 5);
        //deleteUser(6);
        //getAllUsers();

        //new InteractionApi2().getAllUsers();

        //new InteractionApi2().printOpenTasks(1);
        new InteractionApi2().printSaveComments(5);
    }

    public User createUser(String name, String email) {
        return new User(name, email);
    }

    public void uploadUser(User user) throws IOException, InterruptedException {
        String coolKidJson = GSON.toJson(user);;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ALL_USERS))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(coolKidJson))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

    public void updateUser(User user, int id) throws IOException, InterruptedException {
        user.setId(id);

        String coolKidJson = GSON.toJson(user);;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(coolKidJson))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

    public void deleteUser(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + id))
                //.header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

    public void getUserByUsername(String username) throws Exception {
        List<User> user = getList(ALL_USERS).stream().filter(it -> it.getUsername().equals(username)).collect(Collectors.toList());
        System.out.println(user);
    }

    public void getUserById(int id) throws Exception {
        List<User> user = getList(ALL_USERS).stream().filter(it -> it.getId() == id).collect(Collectors.toList());
        System.out.println(user);
    }

    public void getAllUsers() throws Exception {
        for (User user : getList(ALL_USERS)) {
            System.out.println(user);
        }
    }

    public void printOpenTasks(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/todos?userId=" + id))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        Type typeToken = TypeToken.getParameterized(List.class, Todos.class).getType();

        List<Todos> todos = new Gson().fromJson(response.body(), typeToken);

        List<Todos> openTasks = todos.stream().filter(it -> !it.isCompleted()).collect(Collectors.toList());

        for (Todos openTask : openTasks) {
            System.out.println(openTask);
        }
    }

    public void printSaveComments(int id) throws Exception {
        //getting last post id
        HttpRequest requestPosts = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + id + "/posts"))
                .build();
        HttpResponse<String> responsePosts = CLIENT.send(requestPosts, HttpResponse.BodyHandlers.ofString());
        Type typeTokenPosts = TypeToken.getParameterized(List.class, Post.class).getType();
        List<Post> posts = new Gson().fromJson(responsePosts.body(), typeTokenPosts);
        int lastPost = posts.size() - 1;
        int lastPostId = posts.get(lastPost).getId();

        //getting comments to post
        HttpRequest requestComments = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/" + lastPostId + "/comments"))
                .build();
        HttpResponse<String> responseComments = CLIENT.send(requestComments, HttpResponse.BodyHandlers.ofString());
        Type typeTokenComments = TypeToken.getParameterized(List.class, Comment.class).getType();
        List<Comment> comments = new Gson().fromJson(responseComments.body(), typeTokenComments);

        //printing comments
        for (Comment comment : comments) {
            System.out.println(comment);
        }

        //writing comments to file
        String commentsToWrite = responseComments.body();
        String fileName = "user-" + id + "-post-" + lastPostId + "-comments.json";
        File file = new File("./" + fileName);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(commentsToWrite);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private List <User>  getList(String uri) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        Type typeToken = TypeToken.getParameterized(List.class, User.class).getType();

        List<User> users = new Gson().fromJson(response.body(), typeToken);

        return users;
    }
}
