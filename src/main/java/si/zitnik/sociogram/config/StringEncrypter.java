package si.zitnik.sociogram.config;

import java.nio.charset.Charset;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


import org.apache.commons.codec.binary.Base64;

public class StringEncrypter
{
	public static final String DES_ENCRYPTION_SCHEME 	= "DES";
	public static final String ENCRYPTION_KEY			= "Ob bistrem potoku je mlin, a jaz sem na mlinarjev sin. Ko mlinček ropoče in voda šumlja, srce mi veselo igra, igra. Če mlinček pri miru bi stal, bi mlinar in kmet žaloval. In otrok bi jokal ter tožil glasno, kako je brez kruha hudo, hudo.";
	private static final Charset	UNICODE_FORMAT			= Charset.defaultCharset();
	private static final boolean encryptionOn = true;
	
	private KeySpec				keySpec;
	private SecretKeyFactory	keyFactory;
	private Cipher				cipher;
	

	public StringEncrypter() throws Exception
	{
		byte[] keyAsBytes = ENCRYPTION_KEY.getBytes( UNICODE_FORMAT );
		keySpec = new DESKeySpec( keyAsBytes );
		keyFactory = SecretKeyFactory.getInstance( DES_ENCRYPTION_SCHEME );
		cipher = Cipher.getInstance( DES_ENCRYPTION_SCHEME );
	}


	public String encrypt( String unencryptedString ) throws Exception
	{
		if ( unencryptedString == null || unencryptedString.trim().length() == 0 )
				return "";
		if (!encryptionOn){
				return unencryptedString;
		}
		SecretKey key = keyFactory.generateSecret( keySpec );
		cipher.init( Cipher.ENCRYPT_MODE, key );
		byte[] cleartext = unencryptedString.getBytes( UNICODE_FORMAT );
		byte[] ciphertext = cipher.doFinal( cleartext );

		return Base64.encodeBase64String(ciphertext);
	}

	public String decrypt( String encryptedString ) throws Exception 
	{
		if ( encryptedString == null || encryptedString.trim().length() <= 0 )
				return "";
		if (!encryptionOn){
			return encryptedString;
		}
		SecretKey key = keyFactory.generateSecret( keySpec );
		cipher.init( Cipher.DECRYPT_MODE, key );
		Base64 base64decoder = new Base64();
		byte[] cleartext = base64decoder.decode( encryptedString );
		byte[] ciphertext = cipher.doFinal( cleartext );

		return new String(ciphertext);
	}
}