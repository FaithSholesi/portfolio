package music;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.Serializable;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Music implements Serializable {
	private String file;
	
	public Music(String file) {
		this.file=file;
		//this.setFile(file);
	}


	public Music() {
		
	}

	//Just pLay an audio 
	public void PlayMusic(String string) {
		//this.file=file;
		try {
			//FileInputStream fileInputStream = new FileInputStream(this.file);
			FileInputStream fileInputStream = new FileInputStream(file);
			Player player = new Player(fileInputStream);
			player.play();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
