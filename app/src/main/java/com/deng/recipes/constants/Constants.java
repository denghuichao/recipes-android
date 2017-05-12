package com.deng.recipes.constants;

/**
 * Created by Administrator on 2017/2/17.
 */

public final class Constants {
    public final static int Per_Page_Size = 20;

    public final static String baseURL = "http://115.159.65.223:8080/restapi/";

    public final static String Recipe_Service_Recommendation = "/recipes/recommendation";//
    public final static String Recipe_Service_Search = "/recipes/search";//
    public final static String Recipe_Servic_CollectionQuery = "recipes/collections";
    public final static String Recipe_Servic_Add_Collection = "recipes/collections/addition";
    public final static String Recipe_Servic_Reduce_Collection = "recipes/collections/reduction";

    public final static String Recipe_Servic_LikenessQuery = "recipes/likeness";
    public final static String Recipe_Servic_Add_Likeness = "recipes/likeness/addition";
    public final static String Recipe_Servic_Reduce_Likeness = "recipes/likeness/reduction";

    public final static String Recipe_Servic_CooknessQuery = "recipes/cookness";
    public final static String Recipe_Servic_Add_Cookness = "recipes/cookness/addition";
    public final static String Recipe_Servic_Reduce_Cookness = "recipes/cookness/reduction";

    public final static String Recipe_Parameter_Page = "pageIndex";//
    public final static String Recipe_Parameter_Size = "pageSize";//

    public final static String Recipe_Parameter_QueryString = "queryString";
    public static final String Recipe_Parameter_ID = "id";




}
