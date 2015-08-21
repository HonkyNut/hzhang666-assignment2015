package controllers;

import model.*;
import play.*;
import play.mvc.*;
import play.data.*;
import java.util.*;
import play.libs.Json;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.mindrot.jbcrypt.BCrypt;

import views.html.*;

public class Application extends Controller {
	// Users
	private List<User> users = new ArrayList<User>();
	// Messages
	private List<Message> messages = new ArrayList<Message>();
	static String last;

	public Result index() {
		return ok(login.render("Log In", ""));
	}

	public Result login() {
		return ok(login.render("Log In", ""));
	}

	public Result loginCheck() {
		DynamicForm requestData = Form.form().bindFromRequest();
		String email = requestData.get("email");
		String password = requestData.get("password");
		User user = new User(email, password);
		String connected = session("connected");
		if (connected != null && connected.equals(email)) {
			return redirect("/messagehall");
		} else {
			int current = users.indexOf(user);
			if (current >= 0 && BCrypt.checkpw(requestData.get("password"), users.get(current).getPassowrd())) {
				
				session("connected", email);
				return redirect("/messagehall");
			} else {
				//redirect("/messagehall");
				return ok(login.render("Log In", "email or password is error!"));
			}
		}
	}

	public Result register() {
		return ok(register.render("Resigter"));
	}

	public Result addUser() {
		DynamicForm requestData = Form.form().bindFromRequest();
		String email = requestData.get("email");
		String password = requestData.get("password");
		User user = new User(email, password);

		System.out.println(user.toString());
		if (users.indexOf(user) >= 0) {
			return ok(adduser.render("AddUser", "UserName is repeat!"));
		} else {
			users.add(user);
			return ok(adduser.render("Log In", "Register Success"));
		}
	}

	public Result messageHall() {
		String connected = session("connected");
		if (connected != null) {
			return ok(messagehall.render("Message", connected, messages));
		} else {
			return redirect("/login");
		}
	}

	public Result logout() {
		String connected = session("connected");

		if (connected != null) {
			session().remove("connected");
		}
		return redirect("/login");
	}

	public Result postmessage() {
		DynamicForm requestData = Form.form().bindFromRequest();
		String uuid = UUID.randomUUID().toString();
		String email = session("connected");
		String content = requestData.get("content");

		Message message = new Message(uuid, email, content);

		messages.add(message);

		return redirect("/messagehall");
	}

	public Result searchByUsers() {
		DynamicForm requestData = Form.form().bindFromRequest();
		String usersname = requestData.get("user");
		// System.out.println(usersname);
		List<Message> temp = new ArrayList<Message>();
		for (int i = messages.size() - 1; i >= 0; i--) {
			// System.out.println(messages.get(i).getEmail());
			if (messages.get(i).getEmail().equals(usersname)) {
				temp.add(messages.get(i));
			}
		}
		String connected = session("connected");
		return ok(messagehall.render("Message", connected, temp));
	}

	public Result searchByTags(String tag) {
		List<Message> temp = new ArrayList<Message>();
		tag = "#"+tag;
		for (int i = messages.size() - 1; i >= 0; i--) {
			if (messages.get(i).getTags().indexOf(tag)>=0) {
				temp.add(messages.get(i));
			}
		}
		String connected = session("connected");
		return ok(messagehall.render("Message", connected, temp));
	}

	public Result users(String usersname) {
		List<Message> temp = new ArrayList<Message>();
		for (int i = messages.size() - 1; i >= 0; i--) {
			if (messages.get(i).getEmail().equals(usersname)) {
				temp.add(messages.get(i));
			}
		}
		return ok(Json.toJson(temp));
	}

	public Result tags(String tag) {
		List<Message> temp = new ArrayList<Message>();
		tag = "#"+tag;
		for (int i = messages.size() - 1; i >= 0; i--) {
			if (messages.get(i).getTags().indexOf(tag)>=0) {
				temp.add(messages.get(i));
			}
		}
		return ok(Json.toJson(temp));
	}

}
