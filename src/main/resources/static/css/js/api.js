/* =========================
   api.js (공통 fetch 모듈)
========================= */

let currentController = null;

/**
 *
 * @param {*} url
 * @returns
 */
async function apiGet(url) {
  if (currentController) currentController.abort();

  currentController = new AbortController();

  try {
    const res = await fetch(url, { signal: currentController.signal });
    if (!res.ok) throw new Error("HTTP Error");
    return await res.json();
  } catch (e) {
    if (e.name === "AbortError") return null;
    alert("데이터 로딩 실패");
    console.error(e);
  }
}

/*
async function apiGet(url) {
  // 이전 요청 취소
  if (currentController) currentController.abort();
  
  currentController = new AbortController();
  const signal = currentController.signal;

  try {
    const response = await fetch(url, {
      method: "GET",
      signal,
      headers: {
        Accept: "application/json",
      },
    });

    if (!response.ok) {
      throw new Error(`HTTP Error: ${response.status}`);
    }

    return await response.json();
  } catch (error) {
    if (error.name === "AbortError") {
      console.warn("이전 요청 취소됨");
      return null;
    }
    throw error;
  }
}
*/
