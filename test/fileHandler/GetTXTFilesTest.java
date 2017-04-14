package fileHandler;

import org.junit.Before;
import org.junit.Test;
import fileHandler.GetTXTFiles;
import static org.junit.Assert.assertArrayEquals;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GetTXTFilesTest {
	
	GetTXTFiles getFiles;
	
	@Before
	public void setUp() throws IOException {
	String filePath = "test/sampleTexts/";
   	 this.getFiles = new GetTXTFiles();
   	 this.getFiles.setPath(filePath);
   	 this.getFiles.getTxtPaths();
   }
	
	@Test
	public void getTheFiles() throws IOException {
		
		List<Path> expectedPaths = new ArrayList<>();
		expectedPaths.add(Paths.get("/Users/rweinstein/Documents/DevProjects/Java/Readability/test/sampleTexts/GroupA/AliceInWonderland.txt"));
		expectedPaths.add(Paths.get("/Users/rweinstein/Documents/DevProjects/Java/Readability/test/sampleTexts/GroupA/TheTimeMachine.txt"));
		expectedPaths.add(Paths.get("/Users/rweinstein/Documents/DevProjects/Java/Readability/test/sampleTexts/GroupB/GroupC/MobyDick.txt"));
		expectedPaths.add(Paths.get("/Users/rweinstein/Documents/DevProjects/Java/Readability/test/sampleTexts/GroupB/Iliad.txt"));
		
		
		assertArrayEquals("FAIL - did not find all files", expectedPaths.toArray(), this.getFiles.getTxtPaths().toArray());
	}
	

}
