package si.zitnik.sociogram.icons;

import java.awt.Dimension;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;
import si.zitnik.sociogram.util.icon.IcoWrapperRadianceIcon;

public class IconCache {
	//each icon should be used only once in gui - because resizing (if one resizes, all resize )
	
	//vnos podatkov
	public IcoWrapperRadianceIcon dodajOsebo;
	public IcoWrapperRadianceIcon brisiOsebo; 
	public IcoWrapperRadianceIcon izvozOseb; 
	public IcoWrapperRadianceIcon uvoziOsebe; 
	public IcoWrapperRadianceIcon uvoziOsebeIzSociograma; 
	
	//anketa
	public IcoWrapperRadianceIcon addQ; 
	public IcoWrapperRadianceIcon removeQ;
	public IcoWrapperRadianceIcon addQBig;
	public IcoWrapperRadianceIcon removeQBig;
	public IcoWrapperRadianceIcon print; 
	public IcoWrapperRadianceIcon twoPerPage; 
	public IcoWrapperRadianceIcon fourPerPage; 
	public IcoWrapperRadianceIcon twoPerPageTooltip;
	public IcoWrapperRadianceIcon fourPerPageTooltip;
	public IcoWrapperRadianceIcon printNames; 
		
	//graf
	public IcoWrapperRadianceIcon vsi;
	public IcoWrapperRadianceIcon vzajemnoIzbrani;
    public IcoWrapperRadianceIcon vzajemnoIzbraniAll;
    public IcoWrapperRadianceIcon posameznik;
	public IcoWrapperRadianceIcon neizbran;
	public IcoWrapperRadianceIcon ok1; 
	public IcoWrapperRadianceIcon ok2; 
	public IcoWrapperRadianceIcon ok3;
	public IcoWrapperRadianceIcon no1; 
	public IcoWrapperRadianceIcon no2; 
	public IcoWrapperRadianceIcon no3; 
	public IcoWrapperRadianceIcon man; 
	public IcoWrapperRadianceIcon woman; 
	public IcoWrapperRadianceIcon maleSign; 
	public IcoWrapperRadianceIcon femaleSign;
	public IcoWrapperRadianceIcon anonymous;
    public IcoWrapperRadianceIcon word;
    public IcoWrapperRadianceIcon excel;
    public IcoWrapperRadianceIcon image;
	public IcoWrapperRadianceIcon imageBig;
	
	//socio klasifikacija
	public IcoWrapperRadianceIcon pdf; 
	public ImageIcon socioClassSI;
	public ImageIcon socioClassEN;
	public ImageIcon socioClassDE;
	public ImageIcon socioClassHR;
	public IcoWrapperRadianceIcon all; 
	public IcoWrapperRadianceIcon liking; 
	public IcoWrapperRadianceIcon unliking; 
	public IcoWrapperRadianceIcon unseen; 
	public IcoWrapperRadianceIcon controverse; 
	public IcoWrapperRadianceIcon average; 
		
	//common
	public IcoWrapperRadianceIcon socioNov; 
	public IcoWrapperRadianceIcon socioOdpri; 
	public IcoWrapperRadianceIcon socioShrani; 
	public IcoWrapperRadianceIcon socioIzhod; 
	public IcoWrapperRadianceIcon help; 
	public IcoWrapperRadianceIcon info; 
	public IcoWrapperRadianceIcon dummyImage;
	public IcoWrapperRadianceIcon socioLogo;
	public ImageIcon mgaLogo;
    public ImageIcon krimarLogo;
    public ImageIcon sl;
    public ImageIcon hr;
    public ImageIcon en;
	public ImageIcon de;
    public ImageIcon delovneSkupine;
    public ImageIcon solstvo;
    public ImageIcon drustva;
    public ImageIcon demo;
    public ImageIcon buy;

    public IcoWrapperRadianceIcon socioSettings;
    public IcoWrapperRadianceIcon jezikSettings;
    public IcoWrapperRadianceIcon registracijaSettings;
    public IcoWrapperRadianceIcon tipProgramaSettings;


	public IconCache() {
		try {
			socioLogo = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/socioLogo.ico"), 1.0, new Dimension(256,256));

			twoPerPageTooltip = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/twoPerPageToopTip.ico"), 1.0, new Dimension(180,256));
			fourPerPageTooltip = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/fourPerPageToopTip.ico"), 1.0, new Dimension(180,256));
			mgaLogo = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/mgaLogo.png")));
            krimarLogo = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/krimarLogo.png")));
			socioClassSI = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/socioClassSI.png")));
			socioClassEN = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/socioClassEN.png")));
			socioClassDE = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/socioClassDE.png")));
			socioClassHR = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/socioClassHR.png")));
            sl = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/sl.png")));
            hr = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/hr.png")));
            en = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/en.png")));
			de = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/de.png")));
            delovneSkupine = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/delovneSkupine.png")));
            solstvo = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/solstvo.png")));
            drustva = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/drustva.png")));
            demo = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/demo.png")));
            buy = new ImageIcon(ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/buy.png")));


            this.socioSettings = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/settings.ico"), 1.0, new Dimension(256,256));
            this.jezikSettings = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/jezikSettings.ico"), 1.0, new Dimension(256,256));
            this.registracijaSettings = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/registracijaSettings.ico"), 1.0, new Dimension(256,256));
            this.tipProgramaSettings = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/tipProgramaSettings.ico"), 1.0, new Dimension(256,256));

			this.dodajOsebo = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/dodajOsebo.ico"), 1.0, new Dimension(256,256));


			this.brisiOsebo = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/brisiOsebo.ico"), 1.0, new Dimension(256, 256));
			this.izvozOseb = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/izvozOseb.ico"), 1.0, new Dimension(256, 256));
			this.uvoziOsebe = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/uvoziOsebe.ico"), 1.0, new Dimension(256, 256));
			this.uvoziOsebeIzSociograma = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/uvoziOsebeIzSociograma.ico"), 1.0, new Dimension(256, 256));
			this.addQ = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/addQ.ico"), 1.0, new Dimension(128, 128));
			this.removeQ = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/removeQ.ico"), 1.0, new Dimension(128, 128));
			this.addQBig = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/addQ.ico"), 1.0, new Dimension(128, 128));
			this.removeQBig = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/removeQ.ico"), 1.0, new Dimension(128, 128));
			this.print = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/print.ico"), 1.0, new Dimension(128, 128));
			this.twoPerPage = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/twoPerPage.ico"), 1.0, new Dimension(128, 128));
			this.fourPerPage = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/fourPerPage.ico"), 1.0, new Dimension(128, 128));
			this.printNames = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/printNames.ico"), 1.0, new Dimension(256, 256));
			this.vsi = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/vsi.ico"), 1.0, new Dimension(128, 128));
			this.vzajemnoIzbrani = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/vzajemnoIzbrana.ico"), 1.0, new Dimension(128, 128));
            this.vzajemnoIzbraniAll = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/vzajemnoIzbranaall.ico"), 1.0, new Dimension(128, 128));
			this.posameznik = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/posameznik.ico"), 1.0, new Dimension(128, 128));
			this.neizbran = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/neizbran.ico"), 1.0, new Dimension(256, 256));
			this.ok1 = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/ok1.ico"), 1.0, new Dimension(128, 128));
			this.ok2 = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/ok2.ico"), 1.0, new Dimension(256, 256));
			this.ok3 = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/ok3.ico"), 1.0, new Dimension(256, 256));
			this.no1 = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/no1.ico"), 1.0, new Dimension(128, 128));
			this.no2 = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/no2.ico"), 1.0, new Dimension(256, 256));
			this.no3 = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/no3.ico"), 1.0, new Dimension(256, 256));
			this.man = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/man.ico"), 1.0, new Dimension(32, 32));
			this.woman = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/woman.ico"), 1.0, new Dimension(32, 32));
			this.maleSign = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/male.ico"), 1.0, new Dimension(32, 32));
			this.femaleSign = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/female.ico"), 1.0, new Dimension(32, 32));
			this.anonymous = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/anonymous.ico"), 1.0, new Dimension(128, 128));
			this.pdf = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/pdf.ico"), 1.0, new Dimension(192, 192));
			this.all = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/all.ico"), 1.0, new Dimension(80, 64));
			this.liking = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/liking.ico"), 1.0, new Dimension(64, 64));
			this.unliking = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/unliking.ico"), 1.0, new Dimension(64, 64));
			this.unseen = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/unseen.ico"), 1.0, new Dimension(64, 64));
			this.controverse = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/controv.ico"), 1.0, new Dimension(64, 64));
			this.average = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/average.ico"), 1.0, new Dimension(64, 64));
			this.socioNov = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/socioNov.ico"), 1.0, new Dimension(128, 128));
			this.socioOdpri = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/socioOdpri.ico"), 1.0, new Dimension(128, 128));
			this.socioShrani = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/socioShrani.ico"), 1.0, new Dimension(128, 128));
			this.socioIzhod = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/socioIzhod.ico"), 1.0, new Dimension(128, 128));
			this.help = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/help.ico"), 1.0, new Dimension(128, 128));
			this.info = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/info.ico"), 1.0, new Dimension(128, 128));
			this.dummyImage = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/dummyImage.ico"), 1.0, new Dimension(256, 256));
            this.word = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/word.ico"), 1.0, new Dimension(256, 256));
            this.excel = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/excel.ico"), 1.0, new Dimension(256, 256));
            this.image = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/image.ico"), 1.0, new Dimension(256, 256));
			this.imageBig = IcoWrapperRadianceIcon.getIcon(new FileInputStream(RunningUtil.normalizedFilepath+"icons/image.ico"), 1.0, new Dimension(256, 256));
		} catch (IOException e) {
			@SuppressWarnings("unused")
			JErrorDialog errorDialog = new JErrorDialog(I18n.get("imageInitErrorMsg"), e);
		}
	}


}
