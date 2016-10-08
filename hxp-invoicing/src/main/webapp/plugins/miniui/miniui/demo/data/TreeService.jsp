
<%@page import="Test.TreeUtil"%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,Test.*,java.lang.reflect.*"%>
<% 		
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");	
	 
    String methodName = request.getParameter("method");
    
       
    Class[] argsClass = new Class[2];  
    argsClass[0] = HttpServletRequest.class;
    argsClass[1] = HttpServletResponse.class;
     
    Class cls = this.getClass();   
    Method method = cls.getMethod(methodName, argsClass);   
    
    Object[] args = new Object[2];
    args[0] = request;
    args[1] = response;   
    method.invoke(this, args);     
   	
%>
<%!public void LoadTree(HttpServletRequest request, HttpServletResponse response) throws Exception
{ 	
    
    String sql = "select * from plus_file order by updatedate";
    ArrayList folders = new Test.TestDB().DBSelect(sql);
    
    String json = Test.JSON.Encode(folders);
    response.getWriter().write(json);    
}
public void LoadNodes(HttpServletRequest request, HttpServletResponse response) throws Exception
{ 	
    //获取提交的数据
    String id = request.getParameter("id");
    if(StringUtil.isNullOrEmpty(id)) id = "-1";

    //获取下一级节点
    String sql = "select * from plus_file where pid = '" + id + "' order by updatedate";
    ArrayList folders = new Test.TestDB().DBSelect(sql);
    
    //判断节点，是否有子节点。如果有，则处理isLeaf和expanded。
    for (int i = 0, l = folders.size(); i < l; i++)
    {
        HashMap node = (HashMap)folders.get(i);
        String nodeId = node.get("id").toString();

        String sql2 = "select * from plus_file where pid = '" + nodeId + "' order by updatedate";
        ArrayList nodes = new Test.TestDB().DBSelect(sql2);

        if (nodes.size() > 0)
        {
            node.put("isLeaf", false);
            node.put("expanded", false);
        }
    }
    
    //返回处理结果
    String json = Test.JSON.Encode(folders);
    response.getWriter().write(json);    
}
public void SaveTree(HttpServletRequest request, HttpServletResponse response)
{
    String json = request.getParameter("data");
	
    //获得树形数据
    ArrayList tree = (ArrayList)Test.JSON.Decode(json);

  //树形转换为列表
    ArrayList list = TreeUtil.ToList(tree, "-1", "children", "id", "pid");
    
    //首先，将旧的树删除掉

    //然后，将list遍历更新保存到数据库即可...        
    for (int i = 0, l = list.size(); i < l; i++)
    { 
        HashMap node = (HashMap)list.get(i);
        node.put("num", i);    //标记序号，获取的时候order by num一下
        //...save database
    }
}%> 