package com.wanted.lunchmapservice.restaurant.entity;

import com.wanted.lunchmapservice.rating.Rating;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@Entity
public class Restaurant {

    @Id
    @Column(name = "restaurant_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnDefault("'EMPTY'")
    @Column(name = "SIGUN_NM", nullable = false)
    private String sigunNm;

    @ColumnDefault("'EMPTY'")
    @Column(name = "SIGUN_CD", nullable = false)
    private String sigunCd;

    @ColumnDefault("'EMPTY'")
    @Column(name = "BIZPLC_NM", nullable = false)
    private String bizplcNm;

    @ColumnDefault("'EMPTY'")
    @Column(name = "LICENSG_DE", nullable = false)
    private String licensgDe;

    @ColumnDefault("'EMPTY'")
    @Column(name = "BSN_STATE_NM", nullable = false)
    private String bsnStateNm;

    @ColumnDefault("'EMPTY'")
    @Column(name = "CLSBIZ_DE", nullable = false)
    private String clsbizAr;

    @ColumnDefault("'EMPTY'")
    @Column(name = "LOCPLC_AR", nullable = false)
    private String locplcAr;

    @ColumnDefault("'EMPTY'")
    @Column(name = "GRAD_FACLT_DIV_NM", nullable = false)
    private String gradFacltDivNm;

    @ColumnDefault("-1")
    @Column(name = "MALE_ENFLPSN_CNT", nullable = false)
    private Integer maleEnflpsnCnt;

    @ColumnDefault("-1")
    @Column(name = "YY", nullable = false)
    private Integer yy;

    @ColumnDefault("'EMPTY'")
    @Column(name = "MULTI_USE_BIZESTBL_YN", nullable = false)
    private String multiUseBizestblYn;

    @ColumnDefault("'EMPTY'")
    @Column(name = "GRAD_DIV_NM", nullable = false)
    private String gradDivNm;

    @ColumnDefault("'EMPTY'")
    @Column(name = "TOT_FACLT_SCALE", nullable = false)
    private String totFacltScale;

    @ColumnDefault("-1")
    @Column(name = "FEMALE_ENFLPSN_CNT", nullable = false)
    private Integer femaleEnflpsnCnt;

    @ColumnDefault("'EMPTY'")
    @Column(name = "BSNSITE_CIRCUMFR_DIV_NM", nullable = false)
    private String bsnsiteCircumfrDivNm;

    @ColumnDefault("'EMPTY'")
    @Column(name = "SANITTN_INDUTYPE_NM", nullable = false)
    private String sanittnIndutypeNm;

    @ColumnDefault("'EMPTY'")
    @Column(name = "SANITTN_BIZCOND_NM", nullable = false)
    private String sanittnBizcondNm;

    @ColumnDefault("-1")
    @Column(name = "TOT_EMPLY_CNT", nullable = false)
    private Integer totEmplyCnt;

    @ColumnDefault("'EMPTY'")
    @Column(name = "REFINE_LOTNO_ADDR", nullable = false)
    private String refineLotnoArrd;

    @ColumnDefault("'EMPTY'")
    @Column(name = "REFINE_ROADNM_ADDR", nullable = false)
    private String refineRoadnmArrd;

    @ColumnDefault("-1")
    @Column(name = "REFINE_ZIP_CD", nullable = false)
    private Long refineZipCd;

    @ColumnDefault("-1")
    @Column(name = "REFINE_WGS84_LOGT", nullable = false)
    private Double refineWgs84Logt;

    @ColumnDefault("-1")
    @Column(name = "REFINE_WGS84_LAT", nullable = false)
    private Double refineWgs84Lat;

    @ColumnDefault("-1")
    @Column(name = "rating", nullable = false)
    private Double rating;

    @ColumnDefault("'EMPTY'")
    @Column(name = "city_name", nullable = false)
    private String cityName;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<Rating> ratingList;
}
