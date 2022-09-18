import core.Window;

public class Main {
	public static void main(String[] args) {
		Window window = Window.get();
		Thread thread = new Thread(window);
		thread.start();
	}
}
