import core.Window.Window;

public class Main {
	public static void main(String[] args) {
		Window window = Window.get();
		window.init();
		Thread thread = new Thread(window);
		thread.start();
	}
}
