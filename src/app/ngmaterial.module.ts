import {NgModule} from '@angular/core';
import {MatButtonModule, MatCardModule, MatInputModule} from '@angular/material';
import {MatFormFieldModule} from '@angular/material/form-field';


@NgModule({
  imports: [MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule],
  exports: [MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule]
})
export class MaterialAppModule {
}
