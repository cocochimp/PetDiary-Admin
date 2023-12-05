package com.ruoyi.pet.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 宠物类别对象 user_pet
 * 
 * @author ruoyi
 * @date 2023-12-04
 */
public class UserPet extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 宠物id */
    private String petId;

    /** 宠物姓名 */
    @Excel(name = "宠物姓名")
    private String name;

    /** 宠物类型（0猫 1狗） */
    @Excel(name = "宠物类型", readConverterExp = "0=猫,1=狗")
    private String type;

    /** 英文名 */
    private String engName;

    /** 封面图片url */
    @Excel(name = "封面图片url")
    private String img;

    /** 祖籍 */
    @Excel(name = "祖籍")
    private String nation;

    /** 性格 */
    @Excel(name = "性格")
    private String character;

    /** 易患病 */
    private String easyOfDisease;

    /** 寿命 */
    @Excel(name = "寿命")
    private String life;

    /** 价钱 */
    @Excel(name = "价钱")
    private String price;

    /** 简介 */
    @Excel(name = "简介")
    private String des;

    /** 体态特征 */
    private String feature;

    /** 性格特点 */
    private String characterFeature;

    /** 照顾知识 */
    private String careKnowledge;

    /** 喂养要点 */
    private String feedPoints;

    public void setPetId(String petId) 
    {
        this.petId = petId;
    }

    public String getPetId() 
    {
        return petId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setEngName(String engName) 
    {
        this.engName = engName;
    }

    public String getEngName() 
    {
        return engName;
    }
    public void setImg(String img) 
    {
        this.img = img;
    }

    public String getImg() 
    {
        return img;
    }
    public void setNation(String nation) 
    {
        this.nation = nation;
    }

    public String getNation() 
    {
        return nation;
    }
    public void setCharacter(String character) 
    {
        this.character = character;
    }

    public String getCharacter() 
    {
        return character;
    }
    public void setEasyOfDisease(String easyOfDisease) 
    {
        this.easyOfDisease = easyOfDisease;
    }

    public String getEasyOfDisease() 
    {
        return easyOfDisease;
    }
    public void setLife(String life) 
    {
        this.life = life;
    }

    public String getLife() 
    {
        return life;
    }
    public void setPrice(String price) 
    {
        this.price = price;
    }

    public String getPrice() 
    {
        return price;
    }
    public void setDes(String des) 
    {
        this.des = des;
    }

    public String getDes() 
    {
        return des;
    }
    public void setFeature(String feature) 
    {
        this.feature = feature;
    }

    public String getFeature() 
    {
        return feature;
    }
    public void setCharacterFeature(String characterFeature) 
    {
        this.characterFeature = characterFeature;
    }

    public String getCharacterFeature() 
    {
        return characterFeature;
    }
    public void setCareKnowledge(String careKnowledge) 
    {
        this.careKnowledge = careKnowledge;
    }

    public String getCareKnowledge() 
    {
        return careKnowledge;
    }
    public void setFeedPoints(String feedPoints) 
    {
        this.feedPoints = feedPoints;
    }

    public String getFeedPoints() 
    {
        return feedPoints;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("petId", getPetId())
            .append("name", getName())
            .append("type", getType())
            .append("engName", getEngName())
            .append("img", getImg())
            .append("nation", getNation())
            .append("character", getCharacter())
            .append("easyOfDisease", getEasyOfDisease())
            .append("life", getLife())
            .append("price", getPrice())
            .append("des", getDes())
            .append("feature", getFeature())
            .append("characterFeature", getCharacterFeature())
            .append("careKnowledge", getCareKnowledge())
            .append("feedPoints", getFeedPoints())
            .toString();
    }
}
