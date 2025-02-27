import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class UserSession {
    private String username;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;

    public UserSession(String username) {
        this.username = username;
        this.loginTime = LocalDateTime.now();
    }

    public void logout() {
        this.logoutTime = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public String getLoginTime() {
        return formatTime(loginTime);
    }

    public String getLogoutTime() {
        return logoutTime != null ? formatTime(logoutTime) : "Still logged in";
    }

    public String getSessionDuration() {
        if (logoutTime == null) {
            return "Session ongoing";
        }
        Duration duration = Duration.between(loginTime, logoutTime);
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        return minutes + " min " + seconds + " sec";
    }

    private String formatTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return time.format(formatter);
    }
}
