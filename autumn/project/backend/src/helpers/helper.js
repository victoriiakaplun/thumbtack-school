function getIdFromUrl(url) {
  return url.substring(url.lastIndexOf('/') + 1);
}

module.exports = { getIdFromUrl };
