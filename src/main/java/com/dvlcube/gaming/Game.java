package com.dvlcube.gaming;

import static com.dvlcube.gaming.util.Cuber.r;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dvlcube.gaming.util.Range;

/**
 * 
 * @author wonka
 * @since 21/09/2013
 */
public abstract class Game implements Terminatable {

	public boolean debug = false;
	public boolean terminating = false;
	public double scale = 1;
	public final Dimension screen;
	public final Range<Integer> vRange;
	public final Range<Integer> hRange;
	private List<Object> objects = new ArrayList<>();
	private List<ControllableObject> garbage = new ArrayList<>();
	private List<Terminatable> terminatables = new ArrayList<>();
	protected Random random = new Random();

	public Game(Dimension screen) {
		this.screen = screen;
		vRange = r(0, screen.height);
		hRange = r(0, screen.width);
	}

	public Game(Dimension screen, double scale) {
		this(screen);
		this.scale = scale;
	}

	/**
	 * 
	 * @author wonka
	 * @since 21/09/2013
	 */
	public void doLogic() {
		for (Object element : objects) {
			if (element instanceof ControllableObject) {
				ControllableObject controllableObject = (ControllableObject) element;
				if (controllableObject.isGarbage) {
					garbage.add(controllableObject);
				}
			}
			if (element instanceof GameElement)
				((GameElement) element).update();
		}
		gc();
	}

	/**
	 * Garbage collection.
	 * 
	 * @author wonka
	 * @since 03/10/2013
	 */
	private void gc() {
		for (ControllableObject object : garbage) {
			objects.remove(object);
		}
	}

	/**
	 * @param g
	 * @author wonka
	 * @since 21/09/2013
	 */
	public void doGraphics(Graphics2D g) {
		for (Object element : objects) {
			if (element instanceof GameElement)
				((GameElement) element).draw(g);
			else if (element instanceof Shape)
				g.fill(((Shape) element));
		}
	}

	/**
	 * @return the mouse adapter.
	 * @author wonka
	 * @since 21/09/2013
	 */
	public abstract MouseAdapter getMouseAdapter();

	/**
	 * @return the key adapter.
	 * @author wonka
	 * @since 21/09/2013
	 */
	public abstract KeyAdapter getKeyAdapter();

	/**
	 * reset state.
	 * 
	 * @author wonka
	 * @since 21/09/2013
	 */
	public void reset() {

	}

	/**
	 * @param value
	 * @return the value corrected to adjust to the game scale.
	 * @author wonka
	 * @since 28/09/2013
	 */
	public int scale(double value) {
		return (int) (value * scale);
	}

	public String getTitle() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void terminate() {
		System.out.println(this + " terminating");
		terminating = true;
		for (Terminatable terminatable : terminatables) {
			terminatable.terminate();
		}
	}

	/**
	 * @param objects
	 *            of any kind
	 * @author wonka
	 * @since 29/09/2013
	 */
	public void addObject(Object... objects) {
		for (Object object : objects) {
			if (object instanceof Terminatable) {
				terminatables.add((Terminatable) object);
			}
			this.objects.add(object);
			if (object instanceof GameElement)
				((GameElement) object).setSource(this);
		}
	}

	public List<Object> getObjects() {
		return objects;
	}

	protected void mousePressed(MouseEvent e, int mx, int my) {
		int sx = scale(e.getX()), sy = scale(e.getY());
		for (Object element : getObjects()) {
			if (element instanceof Controllable)
				((Controllable) element).mousePressed(e, sx, sy);
		}
	}

	protected void mouseMoved(MouseEvent e, int x, int y) {
		for (Object element : getObjects()) {
			if (element instanceof Controllable)
				((Controllable) element).mouseMoved(e, scale(e.getX()), scale(e.getY()));
		}
	}

	protected void mouseDragged(MouseEvent e, int mx, int my) {
		for (Object element : getObjects()) {
			if (element instanceof Controllable)
				((Controllable) element).mouseDragged(e, scale(e.getX()), scale(e.getY()));
		}
	}

	protected void mouseReleased(MouseEvent e, int mx, int my) {
		for (Object element : getObjects()) {
			if (element instanceof Controllable)
				((Controllable) element).mouseReleased(e, scale(e.getX()), scale(e.getY()));
		}
	}

	protected void mouseWheelMoved(MouseWheelEvent e) {
		for (Object element : getObjects()) {
			if (element instanceof Controllable)
				((Controllable) element).mouseWheelMoved(e);
		}
	}

	protected void keyPressed(KeyEvent e) {
		for (Object element : getObjects()) {
			if (element instanceof Controllable)
				((Controllable) element).keyPressed(e);
		}
	}

	/**
	 * @return the screen working size, after scaling
	 * @author wonka
	 * @since 30/09/2013
	 */
	public Dimension getScaledScreen() {
		return new Dimension(scale(screen.width), scale(screen.height));
	}

	public Point getRandomPoint() {
		int rw = random.nextInt(scale(screen.width));
		int rh = random.nextInt(scale(screen.height));
		return new Point(rw, rh);
	}

	/**
	 * @return scaled screen width.
	 * @author wonka
	 * @since 13/10/2013
	 */
	public int sWidth() {
		return getScaledScreen().width;
	}

	/**
	 * @return scaled screen height.
	 * @author wonka
	 * @since 13/10/2013
	 */
	public int sHeight() {
		return getScaledScreen().height;
	}
}
