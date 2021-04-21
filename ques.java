import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

class Questions extends JFrame implements ActionListener{
	int curr = 0;
	JSplitPane mainPanel;
	JTextArea ques = new JTextArea();
	
	JRadioButton choice1 = new JRadioButton();
        JRadioButton choice2 = new JRadioButton();
       	JRadioButton choice3 = new JRadioButton();
	JRadioButton choice4 = new JRadioButton();
	
	JButton prev = new JButton("Previous");
	JButton submit = new JButton("Submit");
	JButton next = new JButton("Next");
	
	ArrayList<JRadioButton> choices = new ArrayList<JRadioButton>() {{
		add(choice1);	add(choice2);	add(choice3);	add(choice4);}};
	
	ButtonGroup buttonGroup = new ButtonGroup() {{
		add(choice1);	add(choice2);	add(choice3);	add(choice4);}};

	JPanel quesPanel = new JPanel() {{
		add(ques);	add(choice1);	add(choice2);	add(choice3);	add(choice4);}};
	
	JPanel controlPanel = new JPanel() {{
		add(prev);	add(submit);	add(next);}};
	
	private static final String questions[] = {"Which of the following is not an Event Listener?",
						"Which of the following is not a Container in Java?",
						"Which of the following is not a Constructor for JLabel Component?",
						"Which of the following is not required to show the JFrame, but keep the application running?",
						"Which of the following is required to scroll the JScrollPane from left to right, only to keep the frame under the sight?",
						"Which one among the following Classes has the highest hierarchy in Java?",
						"At which unit, the Threads are kept at sleep by default?",
						"Which of the following is not a method of an Applet's Life Cycle?",
						"Does Applets require main() method?",
						"Which of the following is suited for attributes in an interface?"};
	
	private static final String answers[] = {"ComponentListener", "TextListener", "FocusListener", "ToolListener",
						"Web container", "EJB Container", "ROM Container", "Applet Container",
						"JLabel(boolean allign)", "JLabel()", "JLabel(String s)", "JLabel(Icon i)",
						"JFrame.HIDE_ON_CLOSE", "JFrame.EXIT_ON_CLOSE", "JFrame.DISPOSE_ON_CLOSE", "JFrame.DO_NOTHING_ON_CLOSE",
						"JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS", "JScrollPane.HORIZONTAL_SCROLLBAR_NEVER", "JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED", "None of these",
						"Container", "Object", "Lang", "Inherited",
						"MilliSeconds", "MicroSeconds", "Seconds", "None of these",
						"start()", "stop()", "kill()", "init()",
						"Yes", "No", "Can be Customized at Compile Time", "Can be Customized at Run Time",
						"final", "public final", "private static final", "public static final"};
	
	private static final Color crctColor = new Color(0, 204, 0), wrngColor = new Color(255, 0, 0);
	
	private static final int finalAns[] = {4, 3, 1, 1, 3, 2, 1, 3, 2, 4};
	
	private int choosenAns[] = new int[10], done = 55;
	
	Questions() {
		super("Answer the following");
		
        	quesPanel.setLayout(new GridLayout(5, 1));
		quesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
		ques.setFont(new Font("Verdana", Font.PLAIN, 18));
		ques.setEditable(false);
		ques.setOpaque(false);
		ques.setLineWrap(true);
	        ques.setWrapStyleWord(true);
		
		for(JRadioButton choice: choices)
			choice.addActionListener(this);
		
		prev.addActionListener(this);
		submit.addActionListener(this);
		next.addActionListener(this);
		
		mainPanel = new JSplitPane(SwingConstants.HORIZONTAL, quesPanel, controlPanel);
		mainPanel.setDividerLocation(256);
		
		super.add(mainPanel);
		super.setBounds(700, 401, 600, 350);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 2, 2, Color.BLACK));
		super.setResizable(false);
		super.setVisible(true);
		
		this.display();}
	
	public void display() {
		int i = 0;
		buttonGroup.clearSelection();
		ques.setText(curr+1 + ". " + questions[curr]);
		for(JRadioButton choice: choices) {
			choice.setText(answers[curr * 4 + i]);
			choice.setSelected(++i == choosenAns[curr]);}}
	
	@Override public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JRadioButton) {
			done -= choosenAns[curr] == 0 ? curr + 1: 0;
			choosenAns[curr] = choices.indexOf(e.getSource()) + 1;}
		else if(e.getSource() == prev) {
			curr = (curr + 9) % 10;
			this.display();}
		else if(e.getSource() == next) {
			curr = (curr + 1) % 10;
			this.display();}
		else
			if(done != 0) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showConfirmDialog(this, "Complete marking all the answers!", "Submission Unsuccessful!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);}
			else	this.displayResult();}
	
	public int evalResult() {
		int ans = 0;
		for(int i = 0; i < 10; i++)
			ans += choosenAns[i] == finalAns[i] ? 1 : 0;
		return ans;}
	
	public void displayResult() {
		JFrame result = new JFrame("Result");
		result.setBounds(700, 401, 700, 400);
		result.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		result.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
		result.setResizable(false);
		
		JPanel sheet = new JPanel();
		sheet.setLayout(new GridLayout(11, 1, 5, 5));
		sheet.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		
		JTextField score = new JTextField("Total Score: " + this.evalResult() + " / 10");
		score.setHorizontalAlignment(JTextField.CENTER);
		score.setFont(new Font("Verdana", Font.PLAIN, 18));
		score.setEditable(false);
		sheet.add(score);
		
		JTextArea ans;
		for(int i = 0; i < 10; i++) {
			ans = new JTextArea(i+1 + ". " + questions[i] + "\n\nYour Answer: " + answers[4*i+choosenAns[i]-1] + "\nCorrect Answer: " + answers[4*i+finalAns[i]-1]);
			ans.setFont(new Font("Verdana", Font.PLAIN, 18));
			ans.setEditable(false);
			ans.setLineWrap(true);
		        ans.setWrapStyleWord(true);
			ans.setBackground(choosenAns[i] == finalAns[i] ? crctColor : wrngColor);
			sheet.add(ans);}
		
		JScrollPane scrollPane = new JScrollPane(sheet, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		result.add(scrollPane);
		result.setVisible(true);
	}
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch(Exception e) {
			e.printStackTrace();}
		new Questions();}
}