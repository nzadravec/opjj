package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.Segment;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.AscendingAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CreateBlankDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.DescendingAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.InvertCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.LanguageAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PutNameAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.QuitAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.StatisticalInfoAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ToLowercaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ToUppercaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.UniqueAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

public class JNotepadPP extends JFrame {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	private MultipleDocumentModel model;
	
	private Segment segment;
	
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

	private Action createBlankDocumentAction;
	private Action openDocumentAction;
	private Action saveDocumentAction;
	private Action saveAsDocumentAction;
	private Action closeDocumentAction;
	private Action cutTextAction;
	private Action copyTextAction;
	private Action pasteTextAction;
	private Action statisticalInfoAction;
	private Action quitAction;
	private Action croLangAction;
	private Action engLangAction;
	private Action toUppercaseAction;
	private Action toLowercaseAction;
	private Action invertCaseAction;
	private Action ascendingAction;
	private Action descendingAction;
	private Action uniqueAction;

	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				quitAction.actionPerformed(null);
			}

		});

		setLocation(0, 0);
		setSize(600, 600);
		setTitle("JNotepad++");

		initGUI();
	}

	private void initGUI() {
		DefaultMultipleDocumentModel tabbedPane = new DefaultMultipleDocumentModel();
		getContentPane().setLayout(new BorderLayout());
		JPanel tabbedPaneAndStatusBar = new JPanel(new BorderLayout());
		tabbedPaneAndStatusBar.add(tabbedPane, BorderLayout.CENTER);
		model = tabbedPane;
		StatusBar statusBar = new StatusBar(model);
		tabbedPaneAndStatusBar.add(statusBar, BorderLayout.PAGE_END);
		getContentPane().add(tabbedPaneAndStatusBar, BorderLayout.CENTER);

		model.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				setTitle(currentModel);
			}
		});

		createActions();
		createMenus();
		createToolbars();
	}

	private void setTitle(SingleDocumentModel model) {
		Path filePath = model.getFilePath();
		String fileName;
		if (filePath != null) {
			fileName = filePath.getFileName().toString();
		} else {
			fileName = "unnamed";
		}
		setTitle(fileName + " - JNotepad++");
	}

	private void createActions() {
		createBlankDocumentAction = new CreateBlankDocumentAction("create", flp, model);
		openDocumentAction = new OpenDocumentAction("open", flp, this, model);
		saveDocumentAction = new SaveDocumentAction("save", flp, this, model);
		saveAsDocumentAction = new SaveAsDocumentAction("saveAs", flp, this, model);
		closeDocumentAction = new CloseDocumentAction("close", flp, model);
		cutTextAction = new CutTextAction("cut", flp, this, model);
		copyTextAction = new CopyTextAction("copy", flp, this, model);
		pasteTextAction = new PasteTextAction("paste", flp, this, model);
		statisticalInfoAction = new StatisticalInfoAction("info", flp, this, model);
		quitAction = new QuitAction("quit", flp, this, model);
		croLangAction = new LanguageAction("crolang", flp, "hr");
		engLangAction = new LanguageAction("englang", flp, "en");
		toUppercaseAction = new ToUppercaseAction("toUppercase", flp, model);
		toLowercaseAction = new ToLowercaseAction("toLowercase", flp, model);
		invertCaseAction = new InvertCaseAction("invertCase", flp, model);
		ascendingAction = new AscendingAction("ascending", flp, model);
		descendingAction = new DescendingAction("descending", flp, model);
		uniqueAction = new UniqueAction("unique", flp, model);
		
		
		createBlankDocumentAction.putValue(Action.NAME, "New");
		createBlankDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		createBlankDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_0);
		createBlankDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Create a new blank document.");

		openDocumentAction.putValue(Action.NAME, "Open");
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_0);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Open existing file from disk.");

		saveDocumentAction.putValue(Action.NAME, "Save");
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Save current file to disk.");

		saveAsDocumentAction.putValue(Action.NAME, "Save As...");
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift control S"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAsDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Save current file to disk as...");

		closeDocumentAction.putValue(Action.NAME, "Close");
		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		closeDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Save current file to disk as...");

		cutTextAction.putValue(Action.NAME, "Cut");
		cutTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		cutTextAction.putValue(Action.SHORT_DESCRIPTION, "Save current file to disk as...");

		copyTextAction.putValue(Action.NAME, "Copy");
		copyTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		copyTextAction.putValue(Action.SHORT_DESCRIPTION, "Save current file to disk as...");

		pasteTextAction.putValue(Action.NAME, "Paste");
		pasteTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		pasteTextAction.putValue(Action.SHORT_DESCRIPTION, "Save current file to disk as...");

		statisticalInfoAction.putValue(Action.NAME, "Info");
		statisticalInfoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		statisticalInfoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		statisticalInfoAction.putValue(Action.SHORT_DESCRIPTION, "Statistical info of current file.");

		quitAction.putValue(Action.NAME, "Quit");
		quitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		quitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		quitAction.putValue(Action.SHORT_DESCRIPTION, "Exit application.");
		
		croLangAction.putValue(Action.NAME, "Croatian");
		engLangAction.putValue(Action.NAME, "English");
	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(new PutNameAction("file", flp));
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(createBlankDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(statisticalInfoAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.add(new JMenuItem(quitAction));

		JMenu editMenu = new JMenu(new PutNameAction("edit", flp));
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(cutTextAction));
		editMenu.add(new JMenuItem(copyTextAction));
		editMenu.add(new JMenuItem(pasteTextAction));
		
		JMenu toolsMenu = new JMenu(new PutNameAction("tools", flp));
		menuBar.add(toolsMenu);
		
		toolsMenu.add(new JMenuItem(toUppercaseAction));
		toolsMenu.add(new JMenuItem(toLowercaseAction));
		toolsMenu.add(new JMenuItem(invertCaseAction));
		
		JMenu langsMenu = new JMenu("Languages/Jezici/Sprache");
		menuBar.add(langsMenu);
		
		langsMenu.add(new JMenuItem(croLangAction));
		langsMenu.add(new JMenuItem(engLangAction));
		
		JMenu sortMenu = new JMenu(new PutNameAction("sort", flp));
		menuBar.add(sortMenu);
		
		sortMenu.add(new JMenuItem(ascendingAction));
		sortMenu.add(new JMenuItem(descendingAction));
		sortMenu.add(new JMenuItem(uniqueAction));

		setJMenuBar(menuBar);
	}

	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(createBlankDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(openDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutTextAction));
		toolBar.add(new JButton(copyTextAction));
		toolBar.add(new JButton(pasteTextAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(statisticalInfoAction));

		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}

}
