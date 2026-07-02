/**
 * 
 */
function initUI(){
    // 로그인 여부 판단은 sessionStorage 에 "name" 속성이 있는지로 판단
    let name = sessionStorage.getItem("name");
    
    if( name != null ){ // login O
        document.querySelector("#userName").innerHTML = name;
        document.querySelector("#userNameWrapper").style.display="inline";
        document.querySelector("#linkLogout").style.display="inline";
        
        document.querySelector("#linkLogin").style.display="none";
    }else{ // login X
        document.querySelector("#userName").innerHTML = "";
        document.querySelector("#userNameWrapper").style.display="none";
        document.querySelector("#linkLogout").style.display="none";
        
        document.querySelector("#linkLogin").style.display="inline";            
    }       
}
async function logout(){
    // url
    let url = "/auth/logout";
    
    let response = await fetch(url);
    let data = await response.json();
    
    console.log(data);
    
    if( data.result == "success" ){
        // 개별 항목 삭제
//      sessionStorage.removeItem("name");
//      sessionStorage.removeItem("email");
//      sessionStorage.removeItem("roles");   
        
        // 전체 삭제
        sessionStorage.clear();
        
        // 로그아웃에 따른 UI 처리
        initUI();
        
    }else{
        alert("로그아웃 실패");
    }
}