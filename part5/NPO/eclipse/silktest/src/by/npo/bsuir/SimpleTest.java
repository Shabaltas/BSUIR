package by.npo.bsuir;

import com.borland.silktest.jtf.BaseState;
import org.junit.Before;
import org.junit.Test;
import com.borland.silktest.jtf.Window;
import com.borland.silktest.jtf.Menu;
import com.borland.silktest.jtf.common.types.MouseButton;
import com.borland.silktest.jtf.common.types.Point;
import com.borland.silktest.jtf.MenuItem;
import com.borland.silktest.jtf.Tree;
import com.borland.silktest.jtf.PushButton;
import com.borland.silktest.jtf.Desktop;
import com.borland.silktest.jtf.DropDownToolItem;
import com.borland.silktest.jtf.Control;

public class SimpleTest {

	private Desktop desktop = new Desktop();

	@Before
	public void baseState() {
		BaseState baseState = new BaseState();
		baseState.execute(desktop);
	}

	@Test
	public void deleteFileInFolder() {
		//start recording 
		desktop.<Window>find("C  Users Asus BSUIRlabs part5 NPO").setActive();
		desktop.<Menu>find("C  Users Asus BSUIRlabs part5 NPO.File").click(MouseButton.LEFT, new Point(19, 10));
		desktop.<MenuItem>find("C  Users Asus BSUIRlabs part5 NPO.Open Folder…").select();
		desktop.<Tree>find("Select Folder.Tree").select("/Quick access/task5");
		desktop.<PushButton>find("Select Folder.Select Folder").select();
		desktop.<Window>find("untitled (task5) - Sublime Text (UNREGISTERED)").click(MouseButton.LEFT,
				new Point(126, 417));
		desktop.<Window>find("C  Users Asus Desktop task5 withId").click(MouseButton.RIGHT, new Point(126, 417));
		desktop.<MenuItem>find("C  Users Asus Desktop task5 withId.Delete File").select();
		desktop.<Window>find("untitled (task5) - Sublime Text (UNREGISTERED)").close();
		//end recording
	}

	@Test
	public void replaceInFiles() {

		//start recording 
		desktop.<Menu>find("C  Users Asus BSUIRlabs part5 NPO.File").click(MouseButton.LEFT, new Point(21, 12));
		desktop.<Menu>find("C  Users Asus BSUIRlabs part5 NPO.Find").click(MouseButton.LEFT, new Point(21, 12));
		desktop.<MenuItem>find("C  Users Asus BSUIRlabs part5 NPO.Find in Files…").select();
		desktop.<Window>find("C  Users Asus BSUIRlabs part5 NPO").typeKeys("is");
		desktop.<Window>find("C  Users Asus BSUIRlabs part5 NPO").click(MouseButton.LEFT, new Point(746, 942));
		//desktop.<Window>find("C  Users Asus BSUIRlabs part5 NPO").typeKeys("<Left Ctrl+a>");
		desktop.<Window>find("C  Users Asus BSUIRlabs part5 NPO")
				.typeKeys("C:\\Users\\Asus\\BSUIRlabs\\part5\\NPO\\lab6\\2");
		desktop.<Window>find("C  Users Asus BSUIRlabs part5 NPO").click(MouseButton.LEFT, new Point(1822, 975));
		desktop.<PushButton>find("Confirm Replace.Replace").select();
		desktop.<Window>find("C  Users Asus BSUIRlabs part5 NPO2").setActive();
		desktop.<Menu>find("C  Users Asus BSUIRlabs part5 NPO2.File").click(MouseButton.LEFT, new Point(19, 11));
		desktop.<MenuItem>find("C  Users Asus BSUIRlabs part5 NPO2.Save All").select();
		//end recording
	}
}