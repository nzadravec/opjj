package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.actions.ExitAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.OpenAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAsAction;
import hr.fer.zemris.java.hw16.jvdraw.tools.AddingCirclesTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.AddingFilledCirclesTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.AddingLinesTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.AddingTool;

/**
 * Main class.
 * 
 * @author nikola
 *
 */
public class JVDraw extends JFrame {

	private static final long serialVersionUID = 1L;

	private DrawingModel drawingModel;
	private Tool currentTool;

	private Path path;
	
	private ExitAction exitAction;

	/**
	 * Constructor.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});
		
		setLocation(0, 0);
		setSize(800, 800);
		setTitle("JVDraw");
		initGUI();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		drawingModel = new DrawingModelImpl();

		JColorArea fgColorArea = new JColorArea(new Color(Color.red));
		JColorArea bgColorArea = new JColorArea(new Color(Color.blue));
		ColorInfo bottomColorInfo = new ColorInfo(fgColorArea, bgColorArea);
		cp.add(bottomColorInfo, BorderLayout.PAGE_END);

		JToolBar toolBar = new JToolBar();
		cp.add(toolBar, BorderLayout.PAGE_START);
		toolBar.add(fgColorArea);
		toolBar.addSeparator();
		toolBar.add(bgColorArea);

		toolBar.addSeparator();

		addTools(toolBar, fgColorArea, bgColorArea);

		JPanel canvasAndObjectsList = new JPanel(new BorderLayout());
		cp.add(canvasAndObjectsList, BorderLayout.CENTER);

		JDrawingCanvas canvas = new JDrawingCanvas(drawingModel, currentTool);
		canvasAndObjectsList.add(canvas, BorderLayout.CENTER);

		JList<GeometricalObject> objectsList = new JList<>(new DrawingObjectListModel(drawingModel));
		objectsList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				GeometricalObject object = objectsList.getSelectedValue();
				switch (keyCode) {
				case KeyEvent.VK_DELETE:
					drawingModel.remove(object);
					break;
				case KeyEvent.VK_PLUS:
					drawingModel.changeOrder(object, -1);
					objectsList.setSelectedValue(object, true);
					break;
				case KeyEvent.VK_MINUS:
					drawingModel.changeOrder(object, 1);
					objectsList.setSelectedValue(object, true);
					break;

				default:
					break;
				}
			}
		});

		objectsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// double click
				if (e.getClickCount() >= 2) {
					GeometricalObject clicked = objectsList.getSelectedValue();
					GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
					if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "",
							JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage(), "Data was invalid",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});

		canvasAndObjectsList.add(new JScrollPane(objectsList), BorderLayout.LINE_END);

		createMenus();
	}

	/**
	 * Creates menus.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		exitAction = new ExitAction("Exit", drawingModel, this);
		fileMenu.add(new OpenAction("Open", drawingModel, this));
		fileMenu.add(new SaveAction("Save", drawingModel, this));
		fileMenu.add(new SaveAsAction("Save As", drawingModel, this));
		fileMenu.add(new ExportAction("Export", drawingModel, this, exitAction));
		exitAction = new ExitAction("Exit", drawingModel, this);
		fileMenu.add(exitAction);
	}

	private void addTools(JToolBar toolBar, JColorArea fgColorArea, JColorArea bgColorArea) {
		ButtonGroup toolGroup = new ButtonGroup();

		Tool addingLinesTool = new AddingLinesTool(drawingModel, fgColorArea);
		Tool addingCirclesTool = new AddingCirclesTool(drawingModel, fgColorArea);
		Tool addingFilledCirclesTool = new AddingFilledCirclesTool(drawingModel, fgColorArea, bgColorArea);

		AddingTool addingTool = new AddingTool(addingLinesTool);
		currentTool = addingTool;

		JToggleButton lineButton = new JToggleButton("Line");
		toolGroup.add(lineButton);
		toolBar.add(lineButton);
		lineButton.addItemListener((ItemEvent e) -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				addingTool.setTool(addingLinesTool);
			}
		});

		toolGroup.setSelected(lineButton.getModel(), true);

		JToggleButton circleButton = new JToggleButton("Circle");
		toolGroup.add(circleButton);
		toolBar.add(circleButton);
		circleButton.addItemListener((ItemEvent e) -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				addingTool.setTool(addingCirclesTool);
			}
		});

		JToggleButton filledCircleButton = new JToggleButton("Filled circle");
		toolGroup.add(filledCircleButton);
		toolBar.add(filledCircleButton);
		filledCircleButton.addItemListener((ItemEvent e) -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				addingTool.setTool(addingFilledCirclesTool);
			}
		});
	}

	/**
	 * This component is at the JFrame's bottom. Its a listener to two
	 * {@link IColorProvider} instances (one which provides foreground color and one
	 * which provides background color). It displays text showing foreground and
	 * background color as
	 * <code>“Foreground color: (255, 10, 210), background color: (128, 128, 0).”</code>.
	 * In parentheses are given red, green and blue components of the color.
	 * 
	 * @author nikola
	 *
	 */
	private class ColorInfo extends JLabel {

		private static final long serialVersionUID = 1L;

		/**
		 * Foreground color provider
		 */
		private IColorProvider fgColorProvider;
		/**
		 * Background color provider
		 */
		private IColorProvider bgColorProvider;

		/**
		 * Constructor.
		 * 
		 * @param fgColorProvider
		 *            foreground color provider
		 * @param bgColorProvider
		 *            background color provider
		 */
		private ColorInfo(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
			this.fgColorProvider = fgColorProvider;
			this.bgColorProvider = bgColorProvider;

			setColorInfoText();

			ColorChangeListener colorChangeListener = new ColorChangeListener() {
				@Override
				public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
					setColorInfoText();
				}
			};

			fgColorProvider.addColorChangeListener(colorChangeListener);
			bgColorProvider.addColorChangeListener(colorChangeListener);
		}

		/**
		 * Sets text of foreground and background color.
		 */
		private void setColorInfoText() {
			String text = "Foreground color: (" + fgColorProvider.getCurrentColor() + "), background color: ("
					+ bgColorProvider.getCurrentColor() + ").";
			setText(text);
		}

	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Main function.
	 * 
	 * @param args
	 *            command line arguments - not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
	}

}
