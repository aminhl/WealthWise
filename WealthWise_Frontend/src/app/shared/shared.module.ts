import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SpartanInputComponent } from './components/spartan-input/spartan-input.component';
import { NavbarComponent } from './components/navbar/navbar.component';

@NgModule({
  declarations: [NavbarComponent],
  imports: [CommonModule, SpartanInputComponent],
  exports: [SpartanInputComponent, NavbarComponent],
})
export class SharedModule {}
