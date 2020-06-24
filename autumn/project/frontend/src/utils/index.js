import { library } from '@fortawesome/fontawesome-svg-core';
import { faCrow, faStar } from '@fortawesome/free-solid-svg-icons';

// To make icons project-wide accessed
export function initFontAwesomeLibrary() {
  library.add(faCrow, faStar);
}
