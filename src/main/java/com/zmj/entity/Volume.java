package com.zmj.entity;

import java.util.Date;
import java.util.List;

/**
 * @author mengjun
 * @date 2018/12/8 15:31
 * @desc
 */
public class Volume {

    public String id;
    public int title_number;
    public String title_localization_id;
    public String title_localization_default;
    public Date released_at_date;
    public Date released_at_date_ios;
    public List<Painting> paintings;
}
