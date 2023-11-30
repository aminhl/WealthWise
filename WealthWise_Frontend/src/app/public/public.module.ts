import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PublicRoutingModule } from './public-routing.module';
import { SharedModule } from '../shared/shared.module';
import { SignupComponent } from './pages/signup/signup.component';

@NgModule({
  declarations: [
    SignupComponent
  ],
  imports: [CommonModule, PublicRoutingModule, SharedModule],
})
export class PublicModule {}
