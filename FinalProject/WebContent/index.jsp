
<%@ include file="/WEB-INF/jsp/fragments/tags.jspf"%>
<%@page import="ua.nure.teslenko.SummaryTask4.db.Role"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<c:choose>
	<c:when test="${authorizedUser == null}">
		<head>
			<meta http-equiv="refresh" content="0;URL=control?command=login" />
		</head>
	</c:when>
	<c:otherwise>
		<%@ include file="WEB-INF/jsp/fragments/head.jspf"%>
	</c:otherwise>
</c:choose>


<body>
<div class="content">
	<%@ include file="WEB-INF/jsp/fragments/header.jspf"%>

	<h1 align="center">
		<fmt:bundle basename="resources.resource">
			<fmt:message key="index.pageText" />
		</fmt:bundle>
	</h1>
	
	<div><pre>
 
Администратору системы доступен список Врачей по категориям (педиатр, травматолог, хирург, ...) и список Пациентов. Реализовать возможность сортировки

пациентов:

* по алфавиту;
* по дате рождения;

врачей:

* по алфавиту;
* по категории;
* по количеству пациентов.

Администратор регистрирует в системе пациентов и врачей и назначает пациенту врача.

Врач определяет диагноз, делает назначение пациенту (процедуры, лекарства, операции), которые фиксируются в Больничной карте.

Назначение может выполнить Медсестра (процедуры, лекарства) или Врач (любое назначение). Пациент может быть выписан из больницы, при этом фиксируется окончательный диагноз.

(опционально: реализовать возможность сохранения документа с информацией о выписке пациента).
		</pre>
	</div>
	</div>
	<%@ include file="WEB-INF/jsp/fragments/footer.jspf"%>
</body>
</html>