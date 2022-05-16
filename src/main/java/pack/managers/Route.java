package pack.managers;

public class Route {
    public String url;
    public String title;
    public String navbarTitle;
    public int allowedUsers;

    public Route(String url, String title, String navbarTitle, int userIds) {
        this.url = url;
        this.title = title;
        this.allowedUsers = userIds;
        this.navbarTitle = navbarTitle;
    }
}