package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "announcement") @Getter
@Setter
@AllArgsConstructor
public class Announcement {

    @Id
    @SequenceGenerator(name = "seq_announcement", sequenceName = "seq_announcement")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_announcement")
    private Long id;
    @ManyToOne
    private User user;
    private String title;
    @ManyToOne
    private Category category;
    private String text;
    private List<String> images;
}
