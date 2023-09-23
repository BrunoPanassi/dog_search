package com.dogsearch.demo.model;

import com.dogsearch.demo.util.ImageUtil;
import com.dogsearch.demo.util.exception.UtilException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Entity @Data
@Getter @Setter @NoArgsConstructor
public class Image {
    public static final String objectNamePtBr = "Imagens";
    public static final String quantityImageZero = "Quantidade de imagens igual a zero.";

    @Id
    @SequenceGenerator(name = "seq_image", sequenceName = "seq_image")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_image")
    private Long id;

    @Lob
    @Column(name = "image_one", columnDefinition = "LONGBLOB")
    private byte[] imageOne;

    @Lob
    @Column(name = "image_two", columnDefinition = "LONGBLOB")
    private byte[] imageTwo;

    @Lob
    @Column(name = "image_three", columnDefinition = "LONGBLOB")
    private byte[] imageThree;

    @Lob
    @Column(name = "image_four", columnDefinition = "LONGBLOB")
    private byte[] imageFour;

    @Lob
    @Column(name = "image_five", columnDefinition = "LONGBLOB")
    private byte[] imageFive;

    @Lob
    @Column(name = "image_six", columnDefinition = "LONGBLOB")
    private byte[] imageSix;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "announcement_id", referencedColumnName = "id")
    private Announcement announcement;

    public Image(Stream<byte[]> images) {
        List<byte[]> imagesList =  images.toList();
        for (int i = 0; i < imagesList.size(); i++) {
            if (i == 0)
                this.imageOne = imagesList.get(i);
            if (i == 1)
                this.imageTwo = imagesList.get(i);
            if (i == 2)
                this.imageThree = imagesList.get(i);
            if (i == 3)
                this.imageFour = imagesList.get(i);
            if (i == 4)
                this.imageFive = imagesList.get(i);
            if (i == 5)
                this.imageSix = imagesList.get(i);
        }
    }

    public List<byte[]> getAListOfImages() {
        List<byte[]> images = new ArrayList<>();
        int quantityImages = 6;
        for(int i = 0; i < quantityImages; i++) {
            if (i == 0 && this.imageOne != null)
                images.add(ImageUtil.decompressImage(this.imageOne));
            if (i == 1 && this.imageTwo != null)
                images.add(ImageUtil.decompressImage(this.imageTwo));
            if (i == 2 && this.imageThree != null)
                images.add(ImageUtil.decompressImage(this.imageThree));
            if (i == 3 && this.imageFour != null)
                images.add(ImageUtil.decompressImage(this.imageFour));
            if (i == 4 && this.imageFive != null)
                images.add(ImageUtil.decompressImage(this.imageFive));
            if (i == 5 && this.imageSix != null)
                images.add(ImageUtil.decompressImage(this.imageSix));
        }
        return images;
    }

    public static Stream<byte[]> multipartFileArrayToStreamByteArray(MultipartFile[] file) throws Exception {
        if (file.length == 0) {
            UtilException.throwDefault(quantityImageZero);
        }
        return Arrays.stream(file).map(f -> {
            try {
                return ImageUtil.compressImage(f.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
