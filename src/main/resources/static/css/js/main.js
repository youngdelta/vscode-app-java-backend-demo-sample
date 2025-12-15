const qs = (sel) => document.querySelector(sel);

function renderTable(tableId, list) {
  const template = jsrender.templates("#rowTemplate");
  const html = template.render({ list });
  qs(`#${tableId} tbody`).innerHTML = html;
}

async function loadPage(pageNum, pageSize) {
  qs("#loading").style.display = "block";

  const data = await apiGet(
    `/users/ajax?pageNum=${pageNum}&pageSize=${pageSize}`
  );
  if (!data) return;

  // renderTable("basicTable", data.pageHelperBasic.list);
  renderTable("aopTable", data.aopPage.list);
  renderTable("argTable", data.argPage.list);
  renderTable("rowTable", data.rowBoundsMap.list);

  qs("#currentPage").textContent = data.pageNum;
  qs("#totalPages").textContent = data.totalPages;

  qs("#pageNum").innerHTML = Array.from(
    { length: data.totalPages },
    (_, i) =>
      `<option value="${i + 1}" ${i + 1 === data.pageNum ? "selected" : ""}>
        ${i + 1}
       </option>`
  ).join("");

  qs("#loading").style.display = "none";
}

/* 이벤트 바인딩 */
qs("#goBtn").addEventListener("click", () =>
  loadPage(qs("#pageNum").value, qs("#pageSize").value)
);

qs("#prevBtn").addEventListener("click", () =>
  loadPage(
    Math.max(1, +qs("#currentPage").textContent - 1),
    qs("#pageSize").value
  )
);

qs("#nextBtn").addEventListener("click", () =>
  loadPage(+qs("#currentPage").textContent + 1, qs("#pageSize").value)
);
