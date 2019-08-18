// SPDX-FileCopyrightText: ©  Basil Peace
// SPDX-License-Identifier: Apache-2.0
package org.fidata.about.model.jvm.maven

import static org.fidata.about.TestingUtils.getTestLoc
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import junitparams.naming.TestCaseName
import org.fidata.about.model.License
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner)
class MavenAboutTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none()

  @Test
  void testIsAbleToReadMavenAbout() {
    final File testFile = getTestLoc('model/jvm/maven/maven.ABOUT')
    final MavenAbout a = MavenAbout.readFromFile(testFile)

    assert a.inceptionYear.value == '2019, very good year'

    final License l = a.licenses[0]
    assert LicenseExtended.isInstance(l)
    final LicenseExtended le = (LicenseExtended)l
    assert le.comments.value == 'permissive license'
    assert le.distribution.value == 'repo'

    // TODO: + vcs fields
    // + Maven Vcs connection URL

    assert a.organization.name.value == 'Example Inc.'
    assert a.organization.url.value == new URI('https://example.com/')

    assert a.developers.size() == 1
    assert a.developers[0].id.value == 'john_doe'
    assert a.developers[0].name.value == 'John Doe'
    assert a.developers[0].organization.value == 'Example Inc.'
    assert a.developers[0].roles.size() == 2
    assert a.developers[0].roles[0].value == 'owner'
    assert a.developers[0].roles[1].value == 'developer'
    assert a.developers[0].timezone.value == 'America/Argentina/Mendoza'
    assert a.developers[0].properties.size() == 1
    assert a.developers[0].properties['custom_property'].value == 'custom_property_value'

    assert a.contributors.size() == 1
    assert a.contributors[0].name.value == 'Jane Doe'
    assert a.contributors[0].organization.value == 'Samples Ltd'
    assert a.contributors[0].roles.size() == 1
    assert a.contributors[0].roles[0].value == 'developer'
    assert a.contributors[0].timezone.value == 'Asia/China/Beijing'
    assert a.contributors[0].properties.size() == 1
    assert a.contributors[0].properties['sex'].value == 'female'

    assert a.issueManagement.system.value == 'Example BugTracker'
    assert a.issueManagement.url.value == new URI('https://bugs.example.com/maven/')

    assert a.ciManagement.system.value == 'ExampleCI'
    assert a.ciManagement.url.value == new URI('https://ci.example.com/maven/')

    assert a.mailingLists.size() == 1
    assert a.mailingLists[0].subscribe.value == 'subscribe@maillist.example.com'
    assert a.mailingLists[0].unsubscribe.value == 'unsubscribe@maillist.example.com'
    assert a.mailingLists[0].post.value == 'post@maillist.example.com'
    assert a.mailingLists[0].archiveUrl.value == new URI('https://maillist.example.com/archive')
  }

  @Test
  void testProvidesVcsConnectionUrl() {
    final File testFile = getTestLoc('model/jvm/maven/maven.ABOUT')
    final MavenAbout a = MavenAbout.readFromFile(testFile)

    URI vcsConnectionUrl = a.vcsConnectionUrl.value
    assert vcsConnectionUrl.scheme == 'scm'
    assert vcsConnectionUrl.schemeSpecificPart.startsWith('git')
    assert vcsConnectionUrl.schemeSpecificPart.contains('https://git.example.com/maven/')
  }

  @Test
  @Parameters
  @TestCaseName('{0} is immutable')
  void testAllCollectionsAreReadOnly(String fieldName, @ClosureParams(value = SimpleType, options = "MavenAbout") Closure closure) {
    File testFile = getTestLoc('model/jvm/maven/maven.ABOUT')
    MavenAbout a = MavenAbout.readFromFile(testFile)

    thrown.expect(UnsupportedOperationException)
    closure.call(a)
  }

  Object[] parametersForTestAllCollectionsAreReadOnly() {
    Object[] result = [
      [
        'developers',
        { it.developers.add(Developer.builder().build()) }
      ],
      [
        'contributors',
        { it.contributors.add(Contributor.builder().build()) }
      ],
      [
        'mailingLists',
        { it.mailingLists.add(MailingList.builder().build()) }
      ],
    ]*.toArray().toArray()
    assert result.length > 0
    result
  }
}
