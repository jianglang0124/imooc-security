package com.imooc.security.core.validate.code.image;

import com.imooc.security.core.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode extends ValidateCode {

    private BufferedImage bufferedImage;



    public ImageCode(BufferedImage bufferedImage, String code, int expireIn) {
        super(code,expireIn);
        this.bufferedImage = bufferedImage;
    }

    public ImageCode(BufferedImage bufferedImage, String code, LocalDateTime expireTime) {
        super(code,expireTime);
        this.bufferedImage = bufferedImage;
    }

    /*public  boolean isExpired(){
        return  LocalDateTime.now().isAfter(expireTime);
    }
*/
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }


}
