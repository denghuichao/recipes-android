package com.deng.recipes.constants;

/**
 * Created by Administrator on 2017/2/17.
 */

public final class Constants {
    public final static int Per_Page_Size = 50;

    public final static String baseURL = "http://115.159.65.223:8080/";

    public final static String Recipe_Service_Recommendation = "/restapi/recipes/recommendation";//
    public final static String Recipe_Service_Search = "/restapi/recipes/search";//
    public final static String Recipe_Servic_CollectionQuery = "/restapi/recipes/collections";
    public final static String Recipe_Servic_Add_Collection = "/restapi/recipes/collections/addition";
    public final static String Recipe_Servic_Reduce_Collection = "/restapi/recipes/collections/reduction";

    public final static String Recipe_Servic_LikenessQuery = "/restapi/recipes/likeness";
    public final static String Recipe_Servic_Add_Likeness = "/restapi/recipes/likeness/addition";
    public final static String Recipe_Servic_Reduce_Likeness = "/restapi/recipes/likeness/reduction";

    public final static String Recipe_Servic_CooknessQuery = "/restapi/recipes/cookness";
    public final static String Recipe_Servic_Add_Cookness = "/restapi/recipes/cookness/addition";
    public final static String Recipe_Servic_Reduce_Cookness = "/restapi/recipes/cookness/reduction";

    public final static String Recipe_Parameter_Page = "page_index";//
    public final static String Recipe_Parameter_Size = "page_size";//

    public final static String Recipe_Parameter_QueryString = "query_string";
    public static final String Recipe_Parameter_ID = "id";

}
