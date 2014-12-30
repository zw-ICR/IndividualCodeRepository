package test;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;



import com.googlecode.javacv.FrameGrabber.Exception;


public class RegisterShell {

	public static Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Label label_User = null;
	private Button button_Next = null;
	public static Text text_User = null;
	private Label label = null;
	private Label labelTip = null;
	public RegisterShell(){
		createSShell();
	}
	
	public Shell getShell()
	{
		
		return sShell;
	}
	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		sShell.setText("BP_FaceRecognizer_Register");
		sShell.setSize(new Point(533, 317));
		sShell.setLayout(null);
		label_User = new Label(sShell, SWT.NONE);
		label_User.setText("�����û�����");
		label_User.setFont(new Font(Display.getDefault(), "����", 10, SWT.NORMAL));
		label_User.setBounds(new Rectangle(122, 67, 85, 25));
		button_Next = new Button(sShell, SWT.NONE);
		button_Next.setBounds(new Rectangle(114, 140, 297, 42));
		button_Next.setText("��һ��");
		
		labelTip = new Label(getShell(), SWT.NONE);
		labelTip.setBounds(new Rectangle(111, 210, 380, 80));
		labelTip.setFont(new Font(Display.getDefault(), "����", 10, SWT.NORMAL));
		labelTip.setText("�����ɾ����ע����û���1.��data�е�Fortraining.txt�򿪣�\n��ղ�����" +
				"\n2.��data�е�facedata.xml�ü��±��򿪣�\n��գ����µ�һ��<?xml version=?>������");
		
		
		text_User = new Text(sShell, SWT.BORDER);
		text_User.setBounds(new Rectangle(239, 67, 168, 30));
		label = new Label(getShell(), SWT.NONE);
		label.setBounds(new Rectangle(111, 118, 350, 80));
		label.setFont(new Font(Display.getDefault(), "����", 12, SWT.NORMAL));
		label.setText("�벻Ҫ�����Ѵ��ڵ��û������û�����Ҫ���ո�");
		button_Next.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					String userName=RegisterShell.text_User.getText();
					if(userName.isEmpty()==false)
					{
						FaceRecognizer fr=new FaceRecognizer();
						try 
						{
							fr.register(userName);
							LoginShell displayshell=new LoginShell();
							Shell newShell= displayshell.getShell();
							Shell oldShell=RegisterShell.sShell;
							RegisterShell.sShell=newShell;
							RegisterShell.sShell.open();
							oldShell.dispose();
						}
						catch (Exception e1) 
						{
							e1.printStackTrace();
						}
					}
			}
		});
	
	}

}
