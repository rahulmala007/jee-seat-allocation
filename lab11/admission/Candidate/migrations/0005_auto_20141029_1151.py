# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Candidate', '0004_candidate_pdstatus'),
    ]

    operations = [
        migrations.RenameField(
            model_name='candidate',
            old_name='rankSCPC',
            new_name='rankSCPD',
        ),
    ]
