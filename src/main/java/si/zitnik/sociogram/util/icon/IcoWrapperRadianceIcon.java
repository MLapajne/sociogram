package si.zitnik.sociogram.util.icon;

import org.pushingpixels.radiance.common.api.icon.RadianceIcon;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Implementation of {@link RadianceIcon} interface that wraps ICO files.
 *
 * @author Kirill Grouchnikov
 */
public class IcoWrapperRadianceIcon extends IcoWrapperIcon implements RadianceIcon, RadianceIcon.Factory {
    /**
     * Returns the icon for the specified URL.
     *
     * @param location
     *            Icon URL.
     * @param initialDim
     *            Initial dimension of the icon.
     * @return Icon instance.
     */
    public static IcoWrapperRadianceIcon getIcon(URL location, double scale,
                                                 final Dimension initialDim) {
        try {
            return new IcoWrapperRadianceIcon(location.openStream(), scale, initialDim);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the icon for the specified input stream.
     *
     * @param inputStream
     *            Icon input stream.
     * @param initialDim
     *            Initial dimension of the icon.
     * @return Icon instance.
     */
    public static IcoWrapperRadianceIcon getIcon(InputStream inputStream, double scale,
                                                 final Dimension initialDim) {
        return new IcoWrapperRadianceIcon(inputStream, scale, initialDim);
    }

    /**
     * Creates a new ICO-based resizable icon.
     *
     * @param inputStream
     *            Input stream with the ICO content.
     * @param initialDim
     *            Initial dimension of the icon.
     */
    private IcoWrapperRadianceIcon(InputStream inputStream, double scale,
                                   Dimension initialDim) {
        super(inputStream, scale, initialDim.width, initialDim.height);
    }

    @Override
    public void setDimension(Dimension dim) {
        this.setPreferredSize(dim);
    }

    @Override
    public boolean supportsColorFilter() {
        return false;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        throw new UnsupportedOperationException();
    }


    @Override
    /**
     * Factory "hack"
     */
    public RadianceIcon createNewIcon() {
        return this;
    }
}
