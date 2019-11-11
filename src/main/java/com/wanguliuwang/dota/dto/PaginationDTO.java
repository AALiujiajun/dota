package com.wanguliuwang.dota.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEnd;
    private Integer page;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalpage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {

         totalpage = 1;
        if (totalCount % size == 0) {
            totalpage = totalCount / size;
        } else {
            totalpage = totalCount / size + 1;
        }

        if(page<1)
        {
            page=1;
        }
        if(page>totalpage)
        {
            page=totalpage;
        }
        this.page=page;
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        if (page == totalpage) {
            showNext = false;
        } else {
            showNext = true;
        }
        if(pages.contains(1))
        {
            showFirstPage=false;
        }
        else
        {
            showFirstPage=true;
        }
        if(pages.contains(totalpage))
        {
            showEnd=false;
        }
        else
        {
            showEnd=true;
        }

        pages.add(page);
        for(int i=1;i<=3;i++)
        {
            if(page-i>0)
            {
                pages.add(0,page-i);
            }
            if(page +i<=totalpage)
            {
                pages.add(page+i);
            }
        }
    }
}
